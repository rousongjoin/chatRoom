package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * �Ȼ�ȡ����Я���ķ�������ֵ ��ȡָ������ֽ������ ��������Я���ķ�������ֵ����ͨ���ֽ�������ȡָ���ķ��� ���ִ��ָ���ķ���
 * 
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//			// ��ȡ�����ʶ
//			String methodName = request.getParameter("method");
//			// ��ȡָ������ֽ������
//			Class<? extends BaseServlet> clazz = this.getClass();// �����thisָ���Ǽ̳�BaseServlet����
//			// ͨ������ֽ�������ȡ�������ֽ������
//			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//			// �÷���ִ��
//			method.invoke(this, request, response);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1.��ȡ���е�·��������Ĳ���method=�������еķ�����
			String m = request.getParameter("method");
			// �����ȡ��·������û�д�method��������Ļ�,��Ĭ����ת����ҳ,����ԭ���뿴����Ľ���
			if (m == null || m.trim().isEmpty()) {
				m = "index";// �����ƶ�Ϊindex,Ȼ�����������дһ��index����;���м̳�������servlet��������д�������;
			}

			/*
			 * thisΪ��ǰ���������������,�����BaseServlet������,������ָ���BaserServlet;˭����˭�������this
			 * this.getClass()��õ�ǰ�����Ķ��� getMethod(������,�����HttpServletRequest.class,
			 * HttpServletResponse.class�ǹ̶�����,�̶�д��);
			 */

			// ��÷�������
			Method method = this.getClass().getMethod(m, HttpServletRequest.class, HttpServletResponse.class);
			// ��������������ķ������ص��ַ���,ָ����Ҫת�������ض����·��(������servlet���ص�)
			String text = (String) method.invoke(this, request, response);
			// ��������ַ���Ϊnull,˵��ʲô��������
			if (text == null || text.trim().isEmpty()) {
				return;
			}
			// �ַ���.contains("���ַ���") �ַ����Ƿ���������ַ���
			if (!text.contains(":")) {
				// ���緵�ص���"/index.jsp"
				// ���������,˵��û�涨Ҫת�������ض���,����Ĭ��ת��
				request.getRequestDispatcher(text).forward(request, response);
			} else {
				// ����text���ص��� "f:/index.jsp" �� "r:"+request.getContextPath()+"/index.jsp"
				int index = text.indexOf(":");// ����а����Ļ���ȡ:������ַ����е�һ�γ��ֵ�����

				// ��0�±꿪ʼ��ȡ��:��ǰһ���±�Ϊֹ,(��ǰ������) ��ȡ��־f/r
				String bz = text.substring(0, index);// ���ַ�����0�±꿪ʼ��ȡ��ָ��������(��ǰ������)

				// ��ð�ŵ��±�1+1Ϊ2���±��ȡ·�� /index.jsp
				String path = text.substring(index + 1);// ��ָ��������ʼ��ȡ�ַ���,ֱ��ĩβ

				// �����־��f�Ļ�,����Ҫת��
				if (bz.equalsIgnoreCase("f")) {// ���Դ�Сд,����F/f
					request.getRequestDispatcher(path).forward(request, response);// һֱ����дforward
					// �����־��r�Ļ�,����Ҫ�ض���
				} else if (bz.equalsIgnoreCase("r")) {

					response.sendRedirect(path);

				} else {

					throw new RuntimeException("����ָ������");
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ��BaseServlet��дindex����,��ô�̳���������servlet��������д�÷���;
	// ������Ҫ��Ϊ������ĵ�һ��if���Ĭ����ת����ҳ׼����

	public String index(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

}
