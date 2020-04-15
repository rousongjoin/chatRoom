package servlet;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import Impl.LogServiceImpl;
import Impl.UserServiceImpl;
import entity.Log;
import entity.User;
import service.LogService;
import service.UserService;
import util.DateUtils;
import util.IpUtils;
import util.MD5Utils;
import util.UploadUtils;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	//ǰ������
	public String chatRoomUI(HttpServletRequest req, HttpServletResponse res) {
		return "/chatarea.jsp";
	}
	
	//ǰ����־ҳ��
	public String logUI(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		try {
		String name= req.getParameter("name");
		LogService ls = new LogServiceImpl();
		List<Log> logs= ls.getLogs(name);
		req.setAttribute("logs", logs);
		int count = ls.getNum(name);
		req.setAttribute("count", count);
		return "/log.jsp";
		}catch(Exception e){
			e.printStackTrace();
			return "/log.jsp";
		}
	}
	
	//ǰ������ҳ��
	public String aboutUI(HttpServletRequest req,HttpServletResponse res) {
		return "/about.jsp";
	}
	
	//���ǰ��ϵͳ����ҳ��
	public String systemUI(HttpServletRequest req,HttpServletResponse res) {
		return "/systemSetting.jsp";
	}
	
	//������췵������ҳ��
	public String chatUI(HttpServletRequest req, HttpServletResponse res) {
		return "/chat.jsp";
	}
	
	//�����ҳ���ϰ�ť,��ת������ҳ��
	public String informationUI(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ˢ���û���Ϣ
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		try {
		String name= req.getParameter("name");
		UserService us = new UserServiceImpl();
		User user = us.findUserByName(name);
		req.setAttribute("user", user);
		return "/information.jsp";
		}catch(Exception e){
			e.printStackTrace();
			return "/information.jsp";
		}

	}
	
	//�����ҳ���ð�ť����ת������ҳ��
	public String configUI(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		try {
		String name= req.getParameter("name");
		UserService us = new UserServiceImpl();
		User user = us.findUserByName(name);
		req.setAttribute("user", user);
		return "/config.jsp";
		}catch(Exception e){
			e.printStackTrace();
			return "/config.jsp";
		}
	}

	// �����ҳע�ᰴť����ת��ע��ҳ��
	public String registUI(HttpServletRequest req, HttpServletResponse res) {
		return "/register.jsp";
	}

	// �����ҳ��¼��ť����ת����¼ҳ��
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/login.jsp";
	}

	// ������ӣ���ת������ҳ��
	public String activeUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("msg", "�����뼤����");
		return "/active.jsp";
	}

	// �˳�����
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().invalidate();
		// ע���û���ʱ��ֻҪ����invalidate����ע��session�Ϳ�����,����Ҫ��Service��Dao
		return "/index.jsp";
	}

	// ���session״̬
	public String check(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// ��session�л���û�����Ϣ
		User user = (User) req.getSession().getAttribute("user");
		// �ж�session�е��û��Ƿ����
		if (user == null) {
			// ��¼����Ϣ�Ѿ�������!
			res.getWriter().println("1");
		} else {
			// ��¼����Ϣû�й���
			res.getWriter().println("2");
		}
		return null;
	}

	// ������Ϣ
	public String sendMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1.��������
		String from = req.getParameter("from"); // ������
		String to = req.getParameter("to"); // ������
		String color = req.getParameter("color"); // ������ɫ
		String content = req.getParameter("content"); // ��������
		Date date = new Date();
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance();
		String sendTime = sdf.format(date); // ����ʱ��
		// ���ServletContext����.
		ServletContext application = getServletContext();
		// ��ServletContext�л�ȡ��Ϣ
		String sourceMessage = (String) application.getAttribute("message");
		// ��ʽ
		sourceMessage += "<font color='blue'><strong>" + from + "</strong></font><font color='#CC0000'>"
				+ "</font>��<font color='green'>[" + to + "]</font>˵��" + "<font color='" + color + "'>" + content
				+ "</font>��" + sendTime + "��<br>";
		// ����Ϣ���뵽application�ķ�Χ
		application.setAttribute("message", sourceMessage);
		return getMessage(req, resp);
	}

	// ��ȡ������Ϣ����
	public String getMessage(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		String message = (String) getServletContext().getAttribute("message");
		if (message != null) {
			res.getWriter().println(message);
		}
		return null;
	}

	// ���˹���
	public String kick(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1.���ղ���
		String id = req.getParameter("id");
		// 2.����:��userMap�н��û���Ӧ��session����.
		// ���userMap����(�����б�)
		@SuppressWarnings("unchecked")
		Map<User, HttpSession> usermap = (Map<User, HttpSession>) getServletContext().getAttribute("usermap");
		// �������û���Ӧ��session.���֪�����ĸ��û���? id�Ѿ����ݹ���.ȥ���ݿ��в�ѯ.
		// ��дuser��equals �� hashCode ���� ��ôֻҪ�û���id��ͬ����Ϊ��ͬһ���û�.
		User user = new User();
		user.setId(id);
		// ��map�����л���û��Ķ�Ӧ��session
		HttpSession session = usermap.get(user);
		// ����session
		session.invalidate();
		// �ض���ҳ��
		return "/chat.jsp";
	}

	// ע�᷽��
	public String register(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		// ��װ����
		User user = new User();
		try {
			boolean isOk = true;
			// �û���
			String name = req.getParameter("name");
			if (name == null || name.trim().equals("")) {
				isOk = false;
				req.setAttribute("nameError", "�û�������Ϊ�գ�");
				return "/register.jsp";
			}
			user.setName(name);
			// �������
			String password = req.getParameter("password");
			if (password == null || password.trim().equals("")) {
				isOk = false;
				req.setAttribute("passwordError", "���벻��Ϊ�գ�");
				return "/register.jsp";
			}
			String confirmpwd = req.getParameter("confirmpwd");
			if (confirmpwd != null) {
				if (!confirmpwd.equals(password)) {
					isOk = false;
					req.setAttribute("confirmpwdError", "�������벻һ�£�");
					return "/register.jsp";
				}
				user.setPassword(MD5Utils.md5(password));
			}
			// ����
			String e_mail = req.getParameter("e_mail");
			if (e_mail != null && !e_mail.trim().equals("")) {
				if (!e_mail.matches("\\w+@\\w+(\\.\\w+)+")) {
					isOk = false;
					req.setAttribute("e_mailError", "���䲻��һ���Ϸ����䣡");
					return "/register.jsp";
				}
			}
			user.setE_mail(e_mail);
			// ����
			String birthday = req.getParameter("createtime");
			if (birthday != null && !birthday.trim().equals("")) {
				try {
					DateLocaleConverter conver = new DateLocaleConverter();
					conver.convert(birthday);
				} catch (Exception e) {
					isOk = false;
					req.setAttribute("birthError", "���ձ���Ҫ��һ�����ڣ�");
					return "/register.jsp";
				}
			}
			user.setCreatetime(birthday);
			// �Ա�
			user.setSex("man");
			// �û����
			user.setType(0);
			// ����״̬
			user.setState(0);
			// �������id
			user.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			// ������ɼ�����
			user.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			//�����ǳ�
			user.setNickname(name);
			//����ͷ��
			user.setProfilehead(null);
			//���ɼ��
			user.setProfile(null);
			//��������
			user.setAge(String.valueOf(DateUtils.getAgeFromBirthday(birthday)));
			//���ɴ���ʱ��
			user.setFirsttime(DateUtils.getSystemTime());
			//��������¼ʱ��
			user.setLasttime(null);
			// ����ҵ���߼�
			if (isOk == true) {
				UserService us = new UserServiceImpl();
				us.add(user);
			} else {
				req.setAttribute("msg", "ע��ʧ�ܣ�");
				return "/register.jsp";
			}
			req.setAttribute("msg", "ע��ɹ�,��ȥ���伤���");
			return "/msg.jsp";// ����ҪBaseServlet��ת������
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "�ʼ�����ʧ�ܣ���ȷ��������ȷ");
			return "/msg.jsp";// ����ҪBaseServlet��ת������
		}

	}

	/**
	 * �û���¼
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		// ��ȡ������
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// ��Ϊ������ע���ʱ������������,�����ڵ�¼��ʱ����Ҫ���������,��Ȼ�����ݿ��������Բ�����
		password = MD5Utils.md5(password);
		UserService us = new UserServiceImpl();
		User user = us.findUserByUsernameAndPwd(name, password);
		// ������֤��
		String number1 = request.getParameter("number");
		HttpSession session = request.getSession();
		String number2 = (String) session.getAttribute("number");
		if (!number1.equals(number2)) {
			request.setAttribute("number_error", "��֤�����");
			return "/login.jsp";
		}
		// ��ȡ�û���������
		if (user == null) {
			request.setAttribute("inform", "�û������������");
			return "/login.jsp";
		}
		// ��¼�ɹ�
		else {
			Log log =new Log();
			log.setName(name);
			log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			log.setLogtype("��¼");
			log.setDetail("�û���¼");
			log.setTime(DateUtils.getSystemTime());
			log.setIp(IpUtils.getIpAddress(request));
			LogService ls = new LogServiceImpl();
			ls.add(log);
			
			// ��������
			Map<String, String[]> map = request.getParameterMap();
			try {
				
				// ��װ����
				BeanUtils.populate(user, map);
				// �ڶ����û���¼���֮ǰ��session������
				request.getSession().invalidate();
				// �ж��û��Ƿ���map�����У����ڣ�����session����ֹ��ͬ��������¼ͬһ�˻������߳�������
				@SuppressWarnings("unchecked")
				Map<User, HttpSession> usermap = (Map<User, HttpSession>) getServletContext().getAttribute("usermap");
				// �ж��û��Ƿ��Ѿ���map������'
				if (usermap.containsKey(user)) {
					// ˵��map��������û�.
					HttpSession session1 = usermap.get(user);
					// �����session����.
					session1.invalidate();
				}
				// ������ݿ���������û�,�Ͱ�������session��
				// ʹ�ü�������HttpSessionBandingListener������JavaBean�ϵļ�����
				request.getSession().setAttribute("user", user);
				// �����˵���Ϣ���ܿ�����������Ϣ����servletContext����
				ServletContext application = getServletContext();
				String sourceMessage = "";
				if (null != application.getAttribute("message")) {
					sourceMessage = application.getAttribute("message").toString();
				}
				sourceMessage += "ϵͳ���棺<font color='gray'>" + user.getNickname() + "�߽��������ң�</font><br>";
				application.setAttribute("message", sourceMessage);
				response.sendRedirect(request.getContextPath() + "/chat.jsp");
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * �û�����
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		User user = new User();
		try {
			HttpSession session = request.getSession();
			String code2 = (String) session.getAttribute("code");
			String code1 = request.getParameter("code1");
			if (code1.length() == code2.length()) {
				UserService us = new UserServiceImpl();
				user = us.findUserByCode(code1);
				if(user!=null) {
				String name = user.getName();
				Log log =new Log();
				log.setName(name);
				log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
				log.setLogtype("���");
				log.setDetail("�����û�");
				log.setTime(DateUtils.getSystemTime());
				log.setIp(IpUtils.getIpAddress(request));
				LogService ls = new LogServiceImpl();
				ls.add(log);
				return "/login.jsp";// ����ҪBaseServlet��ת������
				}
				else {
				request.setAttribute("msg", "��֤�����,���ػ��ʼ����");
				return "/active.jsp";
				}
			} else {
				request.setAttribute("msg", "��֤�����,���ػ��ʼ����");
				return "/active.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "����ʧ��");
			return "/active.jsp";// ����ҪBaseServlet��ת������
		}
	}
	
	/**
	 * �޸��û���Ϣ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset:utf-8");
		// ��װ����
		try {
			String name = request.getParameter("name");
			String nickname = request.getParameter("nickname");
			String sex = request.getParameter("sex");
			String age = request.getParameter("age");
			String profile = request.getParameter("profile");
			String newProfile=new String(profile.getBytes("ISO-8859-1"),"UTF-8");
			//����ҵ���߼�
			UserService us = new UserServiceImpl();
			us.updateInformation(name, nickname,sex,age,newProfile);
			Log log =new Log();
			log.setName(name);
			log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			log.setLogtype("�޸�");
			log.setDetail("�޸��û���Ϣ");
			log.setTime(DateUtils.getSystemTime());
			log.setIp(IpUtils.getIpAddress(request));
			LogService ls = new LogServiceImpl();
			ls.add(log);
			request.setAttribute("message", "��Ϣ�޸ĳɹ�");
			return "/config.jsp";// ����ҪBaseServlet��ת������
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "��Ϣ�޸�ʧ�ܣ��밴��Ҫ���޸�");
			return "/msg.jsp";// ����ҪBaseServlet��ת������
		}
		
	}
	
	/**
	 * �޸��û�����
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String changePassword(HttpServletRequest request,HttpServletResponse response) throws  Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		//��װ����
		try {
			String name = request.getParameter("name");
			String password=request.getParameter("oldpass");
			password = MD5Utils.md5(password);
			UserService us = new UserServiceImpl();
			User users = us.findUserByUsernameAndPwd(name,password);
			if(users==null) {
				request.setAttribute("error", "ԭʼ�������");
				return "/config.jsp";
			}
			String newpassword = request.getParameter("newpass");
			String newpasswordconfirm = request.getParameter("newpassconfirm");
			if(newpassword.length()!=newpasswordconfirm.length()) {
				request.setAttribute("error", "�������벻һ��");
				return "/config.jsp";// ����ҪBaseServlet��ת������
			}
			else {
				password=MD5Utils.md5(newpassword);
				us.updatePassword(name, password);
				Log log =new Log();
				log.setName(name);
				log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
				log.setLogtype("�޸�");
				log.setDetail("�޸��û�����");
				log.setTime(DateUtils.getSystemTime());
				log.setIp(IpUtils.getIpAddress(request));
				LogService ls = new LogServiceImpl();
				ls.add(log);
				request.setAttribute("message", "��Ϣ�޸ĳɹ�");
				return "/config.jsp";
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "��Ϣ�޸�ʧ�ܣ��밴��Ҫ���޸�");
			return "/msg.jsp";// ����ҪBaseServlet��ת������
		}
	}
	
	/**
	 * �����û�ͷ��
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updatePicture(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		try {
			String name = request.getParameter("name");
			String file = getServletContext().getRealPath("/head_img/");
			Map<String,String> map = new UploadUtils().File_upload(request,file);
			User users = new User();
			try {
	            BeanUtils.populate(users,map);
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	        System.out.println("servlet��ȡ��img����Ϊ:"+users.getProfilehead());
	        String profilehead=users.getProfilehead();
			UserService us = new UserServiceImpl();
			User user = us.updatePicture(name, profilehead);
            if(user!=null){
            	Log log =new Log();
				log.setName(name);
				log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
				log.setLogtype("�޸�");
				log.setDetail("�޸��û�ͷ��");
				log.setTime(DateUtils.getSystemTime());
				log.setIp(IpUtils.getIpAddress(request));
				LogService ls = new LogServiceImpl();
				ls.add(log);
				request.setAttribute("message", "ͷ����³ɹ�!");
				return "/config.jsp";
            }else{
            	request.setAttribute("error", "ͷ�����ʧ��!");
            	return "/config.jsp";
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("error", "����������!");
			return "/config.jsp";
		}
	}

}
