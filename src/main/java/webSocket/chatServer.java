package webSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import Impl.UserServiceImpl;
import entity.User;
import service.UserService;
import servlet.BaseServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chatServer", configurator = HttpSessionConfigurator.class)
public class chatServer  extends BaseServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int onlineCount = 0; //��̬������������¼��ǰ������������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
    private static CopyOnWriteArraySet<chatServer> webSocketSet = new CopyOnWriteArraySet<chatServer>();
    private Session session;    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
    private String name1;      //�û���
    private HttpSession httpSession;    //request��session

    private static List<Object> list = new ArrayList<Object>();   //�����б�,��¼�û�����
    private static Map<Object, Object> routetab = new HashMap<Object, Object>();  //�û�����websocket��session�󶨵�·�ɱ�

  
    
    //ǰ������
  	public String chatRoomUI(HttpServletRequest req, HttpServletResponse res) {
  		return "/chatarea.jsp";
  	}
    /**
     * ���ӽ����ɹ����õķ���
     * @param session  ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     * @throws Exception 
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception{
    	System.out.println("���ӳɹ�");
        UserService us = new UserServiceImpl();
        
        this.session = session;
        System.out.println(session);
        webSocketSet.add(this);     //����set��
        addOnlineCount();           //��������1;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        name1=(String) httpSession.getAttribute("name");
        System.out.println(name1);
        User users = us.findUserByName(name1);
        String nickname=users.getNickname();
        list.add(nickname);           //���û������������б�
        routetab.put(name1, session);   //���û�����session�󶨵�·�ɱ�
        System.out.println(routetab.toString());
        String message = getMessage("[" + nickname + "]����������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice",  list);
        broadcast(message);     //�㲥
    }

    /**
     * ���ӹرյ��õķ���
     * @throws Exception 
     */
    @OnClose
    public void onClose(EndpointConfig config) throws Exception{
    	this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        name1=(String) httpSession.getAttribute("name");
        UserService us = new UserServiceImpl();
        webSocketSet.remove(this);  //��set��ɾ��
        System.out.println(name1);
        User users = us.findUserByName(name1);
        String nickname=users.getNickname();
        subOnlineCount();           //��������1
        list.remove(nickname);        //�������б��Ƴ�����û�
        routetab.remove(name1);
        String message = getMessage("[" + nickname +"]�뿪��������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice", list);
        broadcast(message);         //�㲥
    }

    /**
     * ���տͻ��˵�message,�ж��Ƿ��н����˶�ѡ����й㲥����ָ������
     * @param _message �ͻ��˷��͹�������Ϣ
     * @throws Exception 
     */
    @OnMessage
    public void onMessage(String _message) throws Exception {
        JSONObject chat = JSON.parseObject(_message);
        JSONObject message = JSON.parseObject(chat.get("message").toString());
        System.out.println(message);
        if(message.get("to") == null || message.get("to").equals("")){      //���toΪ��,��㲥;�����Ϊ��,���ָ�����û�������Ϣ
            broadcast(_message);
        }else{
            String [] userlist = message.get("to").toString().split(",");
            System.out.println(userlist.toString());
            this.name1=(String) httpSession.getAttribute("name");    //��ȡ��ǰ�û�
            System.out.println(name1);
            singleSend(_message, (Session) routetab.get(name1));      //���͸��Լ�,���������
            for(String nickname : userlist){
            	UserService us = new UserServiceImpl();
            	User user=us.findUserByName(name1);
                @SuppressWarnings("unused")
				String nick=user.getNickname();
                if(!nickname.equals(message.get("nick"))){
                	User users = us.findUserByNickname(nickname);
                    String name=users.getName();
                    System.out.println("���Ͷ���"+name);
                    singleSend(_message, (Session) routetab.get(name));     //�ֱ��͸�ÿ��ָ���û�
                }
            }
        }
    }

    /**
     * ��������ʱ����
     * @param error
     */
    @OnError
    public void onError(Throwable error){
        error.printStackTrace();
    }

    /**
     * �㲥��Ϣ
     * @param message
     */
    public void broadcast(String message){
        for(chatServer chat: webSocketSet){
            try {
                chat.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * ���ض��û�������Ϣ
     * @param message
     * @param session
     */
    public void singleSend(String message, Session session){
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��װ���ظ�ǰ̨����Ϣ
     * @param message   ������Ϣ
     * @param type      ��Ϣ����
     * @param list      �����б�
     * @return
     */
    public String getMessage(String message, String type, List<Object> list){
        JSONObject member = new JSONObject();
        member.put("message", message);
        member.put("type", type);
        member.put("list", list);
        return member.toString();
    }

    public  int getOnlineCount() {
        return onlineCount;
    }

    public  void addOnlineCount() {
        chatServer.onlineCount++;
    }

    public  void subOnlineCount() {
        chatServer.onlineCount--;
    }
    
    public void head(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			System.out.println("ͷ��");
			User user=new User();
			String nickname=request.getParameter("nickname");
			UserService us= new UserServiceImpl();
            user = us.findUserByNickname(nickname);
            String path = user.getProfilehead();
            String rootPath = request.getSession().getServletContext().getRealPath("/head_img/");
            String picturePath = rootPath + path;
            response.setContentType("image/jpeg; charset=UTF-8");
            ServletOutputStream outputStream = response.getOutputStream();
            FileInputStream inputStream = new FileInputStream(picturePath);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            outputStream = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
