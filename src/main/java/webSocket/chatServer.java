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
	private static int onlineCount = 0; //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static CopyOnWriteArraySet<chatServer> webSocketSet = new CopyOnWriteArraySet<chatServer>();
    private Session session;    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String name1;      //用户名
    private HttpSession httpSession;    //request的session

    private static List<Object> list = new ArrayList<Object>();   //在线列表,记录用户名称
    private static Map<Object, Object> routetab = new HashMap<Object, Object>();  //用户名和websocket的session绑定的路由表

  
    
    //前往聊天
  	public String chatRoomUI(HttpServletRequest req, HttpServletResponse res) {
  		return "/chatarea.jsp";
  	}
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws Exception 
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception{
    	System.out.println("连接成功");
        UserService us = new UserServiceImpl();
        
        this.session = session;
        System.out.println(session);
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        name1=(String) httpSession.getAttribute("name");
        System.out.println(name1);
        User users = us.findUserByName(name1);
        String nickname=users.getNickname();
        list.add(nickname);           //将用户名加入在线列表
        routetab.put(name1, session);   //将用户名和session绑定到路由表
        System.out.println(routetab.toString());
        String message = getMessage("[" + nickname + "]加入聊天室,当前在线人数为"+getOnlineCount()+"位", "notice",  list);
        broadcast(message);     //广播
    }

    /**
     * 连接关闭调用的方法
     * @throws Exception 
     */
    @OnClose
    public void onClose(EndpointConfig config) throws Exception{
    	this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        name1=(String) httpSession.getAttribute("name");
        UserService us = new UserServiceImpl();
        webSocketSet.remove(this);  //从set中删除
        System.out.println(name1);
        User users = us.findUserByName(name1);
        String nickname=users.getNickname();
        subOnlineCount();           //在线数减1
        list.remove(nickname);        //从在线列表移除这个用户
        routetab.remove(name1);
        String message = getMessage("[" + nickname +"]离开了聊天室,当前在线人数为"+getOnlineCount()+"位", "notice", list);
        broadcast(message);         //广播
    }

    /**
     * 接收客户端的message,判断是否有接收人而选择进行广播还是指定发送
     * @param _message 客户端发送过来的消息
     * @throws Exception 
     */
    @OnMessage
    public void onMessage(String _message) throws Exception {
        JSONObject chat = JSON.parseObject(_message);
        JSONObject message = JSON.parseObject(chat.get("message").toString());
        System.out.println(message);
        if(message.get("to") == null || message.get("to").equals("")){      //如果to为空,则广播;如果不为空,则对指定的用户发送消息
            broadcast(_message);
        }else{
            String [] userlist = message.get("to").toString().split(",");
            System.out.println(userlist.toString());
            this.name1=(String) httpSession.getAttribute("name");    //获取当前用户
            System.out.println(name1);
            singleSend(_message, (Session) routetab.get(name1));      //发送给自己,这个别忘了
            for(String nickname : userlist){
            	UserService us = new UserServiceImpl();
            	User user=us.findUserByName(name1);
                @SuppressWarnings("unused")
				String nick=user.getNickname();
                if(!nickname.equals(message.get("nick"))){
                	User users = us.findUserByNickname(nickname);
                    String name=users.getName();
                    System.out.println("发送对象："+name);
                    singleSend(_message, (Session) routetab.get(name));     //分别发送给每个指定用户
                }
            }
        }
    }

    /**
     * 发生错误时调用
     * @param error
     */
    @OnError
    public void onError(Throwable error){
        error.printStackTrace();
    }

    /**
     * 广播消息
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
     * 对特定用户发送消息
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
     * 组装返回给前台的消息
     * @param message   交互信息
     * @param type      信息类型
     * @param list      在线列表
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
			System.out.println("头像");
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
