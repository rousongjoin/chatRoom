package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 先获取请求携带的方法参数值 获取指定类的字节码对象 根据请求携带的方法参数值，再通过字节码对象获取指定的方法 最后执行指定的方法
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
//			// 获取请求标识
//			String methodName = request.getParameter("method");
//			// 获取指定类的字节码对象
//			Class<? extends BaseServlet> clazz = this.getClass();// 这里的this指的是继承BaseServlet对象
//			// 通过类的字节码对象获取方法的字节码对象
//			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//			// 让方法执行
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
			// 1.获取表单中的路径后面带的参数method=方法名中的方法名
			String m = request.getParameter("method");
			// 如果获取的路径后面没有带method这个参数的话,就默认跳转到首页,具体原因请看后面的解释
			if (m == null || m.trim().isEmpty()) {
				m = "index";// 把名称定为index,然后在这个类中写一个index方法;所有继承这个类的servlet都可以重写这个方法;
			}

			/*
			 * this为当前调用这个方法的类,即这个BaseServlet的子类,而不是指这个BaserServlet;谁调用谁就是这个this
			 * this.getClass()获得当前这个类的对象 getMethod(方法名,后面的HttpServletRequest.class,
			 * HttpServletResponse.class是固定参数,固定写法);
			 */

			// 获得方法对象
			Method method = this.getClass().getMethod(m, HttpServletRequest.class, HttpServletResponse.class);
			// 调用这个请求后处理的方法返回的字符串,指的是要转发还是重定向的路径(在子类servlet返回的)
			String text = (String) method.invoke(this, request, response);
			// 如果返回字符串为null,说明什么都不用做
			if (text == null || text.trim().isEmpty()) {
				return;
			}
			// 字符串.contains("子字符串") 字符串是否包含该子字符串
			if (!text.contains(":")) {
				// 比如返回的是"/index.jsp"
				// 如果不包含,说明没规定要转发还是重定向,所以默认转发
				request.getRequestDispatcher(text).forward(request, response);
			} else {
				// 比如text返回的是 "f:/index.jsp" 或 "r:"+request.getContextPath()+"/index.jsp"
				int index = text.indexOf(":");// 如果有包含的话获取:在这个字符串中第一次出现的索引

				// 从0下标开始截取到:的前一个下标为止,(包前不包后) 获取标志f/r
				String bz = text.substring(0, index);// 从字符串的0下标开始截取到指定的索引(包前不包后)

				// 从冒号的下标1+1为2的下标获取路径 /index.jsp
				String path = text.substring(index + 1);// 从指定索引开始截取字符串,直到末尾

				// 如果标志是f的话,就是要转发
				if (bz.equalsIgnoreCase("f")) {// 忽略大小写,比如F/f
					request.getRequestDispatcher(path).forward(request, response);// 一直忘了写forward
					// 如果标志是r的话,就是要重定向
				} else if (bz.equalsIgnoreCase("r")) {

					response.sendRedirect(path);

				} else {

					throw new RuntimeException("您的指令有误");
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 在BaseServlet中写index方法,那么继承他的所有servlet都可以重写该方法;
	// 这里主要是为了上面的第一个if语句默认跳转到首页准备的

	public String index(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

}
