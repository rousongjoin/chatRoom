package webSocket;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;


public class chatServer {
	private static int onlineCount = 0; //��̬������������¼��ǰ������������Ӧ�ð�����Ƴ��̰߳�ȫ�ġ�
    private static CopyOnWriteArraySet<chatServer> webSocketSet = new CopyOnWriteArraySet<chatServer>();
    private Session session;    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
    private String userid;      //�û���
    private HttpSession httpSession;    //request��session

    private static List<Object> list = new ArrayList<Object>();   //�����б�,��¼�û�����
    private static Map<Object, Object> routetab = new HashMap<Object, Object>();  //�û�����websocket��session�󶨵�·�ɱ�

    /**
     * ���ӽ����ɹ����õķ���
     * @param session  ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        webSocketSet.add(this);     //����set��
        addOnlineCount();           //��������1;
        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.userid=(String) httpSession.getAttribute("userid");    //��ȡ��ǰ�û�
        list.add(userid);           //���û������������б�
        routetab.put(userid, session);   //���û�����session�󶨵�·�ɱ�
        String message = getMessage("[" + userid + "]����������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice",  list);
        broadcast(message);     //�㲥
    }

    /**
     * ���ӹرյ��õķ���
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //��set��ɾ��
        subOnlineCount();           //��������1
        list.remove(userid);        //�������б��Ƴ�����û�
        routetab.remove(userid);
        String message = getMessage("[" + userid +"]�뿪��������,��ǰ��������Ϊ"+getOnlineCount()+"λ", "notice", list);
        broadcast(message);         //�㲥
    }

    /**
     * ���տͻ��˵�message,�ж��Ƿ��н����˶�ѡ����й㲥����ָ������
     * @param _message �ͻ��˷��͹�������Ϣ
     */
    @OnMessage
    public void onMessage(String _message) {
        JSONObject chat = JSON.parseObject(_message);
        JSONObject message = JSON.parseObject(chat.get("message").toString());
        if(message.get("to") == null || message.get("to").equals("")){      //���toΪ��,��㲥;�����Ϊ��,���ָ�����û�������Ϣ
            broadcast(_message);
        }else{
            String [] userlist = message.get("to").toString().split(",");
            singleSend(_message, (Session) routetab.get(message.get("from")));      //���͸��Լ�,���������
            for(String user : userlist){
                if(!user.equals(message.get("from"))){
                    singleSend(_message, (Session) routetab.get(user));     //�ֱ��͸�ÿ��ָ���û�
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

}
