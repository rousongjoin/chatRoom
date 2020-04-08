package listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import entity.User;

/**
 * ����ServletContext����Ĵ���������
 *
 */
public class servletContextListener implements ServletContextListener{
	
	public void contextDestroyed(ServletContextEvent sce) {
		
		
	}
	// ServletContext���󴴽� ������������ͻ�ִ��
	// ServletContextEvent�¼�����. ����������---��ServletContext����.(�¼�Դ)
	public void contextInitialized(ServletContextEvent sce) {
		Map<User,HttpSession> usermap=new HashMap<User, HttpSession>();
		sce.getServletContext().setAttribute("usermap", usermap);
	}

}
