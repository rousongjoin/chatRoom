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
	
	//前往聊天
	public String chatRoomUI(HttpServletRequest req, HttpServletResponse res) {
		return "/chatarea.jsp";
	}
	
	//前往日志页面
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
	
	//前往关于页面
	public String aboutUI(HttpServletRequest req,HttpServletResponse res) {
		return "/about.jsp";
	}
	
	//点击前往系统设置页面
	public String systemUI(HttpServletRequest req,HttpServletResponse res) {
		return "/systemSetting.jsp";
	}
	
	//点击聊天返回聊天页面
	public String chatUI(HttpServletRequest req, HttpServletResponse res) {
		return "/chat.jsp";
	}
	
	//点击主页资料按钮,跳转到资料页面
	public String informationUI(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//刷新用户信息
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
	
	//点击主页设置按钮，跳转到设置页面
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

	// 点击主页注册按钮，跳转到注册页面
	public String registUI(HttpServletRequest req, HttpServletResponse res) {
		return "/register.jsp";
	}

	// 点击主页登录按钮，跳转到登录页面
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/login.jsp";
	}

	// 点击连接，跳转到激活页面
	public String activeUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("msg", "请输入激活码");
		return "/active.jsp";
	}

	// 退出操作
	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().invalidate();
		// 注销用户的时候只要调用invalidate方法注销session就可以了,不需要走Service和Dao
		return "/index.jsp";
	}

	// 检测session状态
	public String check(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 从session中获得用户的信息
		User user = (User) req.getSession().getAttribute("user");
		// 判断session中的用户是否过期
		if (user == null) {
			// 登录的信息已经过期了!
			res.getWriter().println("1");
		} else {
			// 登录的信息没有过期
			res.getWriter().println("2");
		}
		return null;
	}

	// 发送消息
	public String sendMessage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1.接收数据
		String from = req.getParameter("from"); // 发言人
		String to = req.getParameter("to"); // 接收者
		String color = req.getParameter("color"); // 字体颜色
		String content = req.getParameter("content"); // 发言内容
		Date date = new Date();
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateTimeInstance();
		String sendTime = sdf.format(date); // 发言时间
		// 获得ServletContext对象.
		ServletContext application = getServletContext();
		// 从ServletContext中获取消息
		String sourceMessage = (String) application.getAttribute("message");
		// 格式
		sourceMessage += "<font color='blue'><strong>" + from + "</strong></font><font color='#CC0000'>"
				+ "</font>对<font color='green'>[" + to + "]</font>说：" + "<font color='" + color + "'>" + content
				+ "</font>（" + sendTime + "）<br>";
		// 将消息存入到application的范围
		application.setAttribute("message", sourceMessage);
		return getMessage(req, resp);
	}

	// 获取聊天消息功能
	public String getMessage(HttpServletRequest req, HttpServletResponse res) throws IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		String message = (String) getServletContext().getAttribute("message");
		if (message != null) {
			res.getWriter().println(message);
		}
		return null;
	}

	// 踢人功能
	public String kick(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1.接收参数
		String id = req.getParameter("id");
		// 2.踢人:从userMap中将用户对应的session销毁.
		// 获得userMap集合(在线列表)
		@SuppressWarnings("unchecked")
		Map<User, HttpSession> usermap = (Map<User, HttpSession>) getServletContext().getAttribute("usermap");
		// 获得这个用户对应的session.如何知道是哪个用户呢? id已经传递过来.去数据库中查询.
		// 重写user的equals 和 hashCode 方法 那么只要用户的id相同就认为是同一个用户.
		User user = new User();
		user.setId(id);
		// 从map集合中获得用户的对应的session
		HttpSession session = usermap.get(user);
		// 销毁session
		session.invalidate();
		// 重定向到页面
		return "/chat.jsp";
	}

	// 注册方法
	public String register(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");
		// 封装数据
		User user = new User();
		try {
			boolean isOk = true;
			// 用户名
			String name = req.getParameter("name");
			if (name == null || name.trim().equals("")) {
				isOk = false;
				req.setAttribute("nameError", "用户名不能为空！");
				return "/register.jsp";
			}
			user.setName(name);
			// 密码加密
			String password = req.getParameter("password");
			if (password == null || password.trim().equals("")) {
				isOk = false;
				req.setAttribute("passwordError", "密码不能为空！");
				return "/register.jsp";
			}
			String confirmpwd = req.getParameter("confirmpwd");
			if (confirmpwd != null) {
				if (!confirmpwd.equals(password)) {
					isOk = false;
					req.setAttribute("confirmpwdError", "两次密码不一致！");
					return "/register.jsp";
				}
				user.setPassword(MD5Utils.md5(password));
			}
			// 邮箱
			String e_mail = req.getParameter("e_mail");
			if (e_mail != null && !e_mail.trim().equals("")) {
				if (!e_mail.matches("\\w+@\\w+(\\.\\w+)+")) {
					isOk = false;
					req.setAttribute("e_mailError", "邮箱不是一个合法邮箱！");
					return "/register.jsp";
				}
			}
			user.setE_mail(e_mail);
			// 生日
			String birthday = req.getParameter("createtime");
			if (birthday != null && !birthday.trim().equals("")) {
				try {
					DateLocaleConverter conver = new DateLocaleConverter();
					conver.convert(birthday);
				} catch (Exception e) {
					isOk = false;
					req.setAttribute("birthError", "生日必须要是一个日期！");
					return "/register.jsp";
				}
			}
			user.setCreatetime(birthday);
			// 性别
			user.setSex("man");
			// 用户类别
			user.setType(0);
			// 激活状态
			user.setState(0);
			// 随机生成id
			user.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			// 随机生成激活码
			user.setCode(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			//生成昵称
			user.setNickname(name);
			//生成头像
			user.setProfilehead(null);
			//生成简介
			user.setProfile(null);
			//生成年龄
			user.setAge(String.valueOf(DateUtils.getAgeFromBirthday(birthday)));
			//生成创建时间
			user.setFirsttime(DateUtils.getSystemTime());
			//生成最后登录时间
			user.setLasttime(null);
			// 调整业务逻辑
			if (isOk == true) {
				UserService us = new UserServiceImpl();
				us.add(user);
			} else {
				req.setAttribute("msg", "注册失败！");
				return "/register.jsp";
			}
			req.setAttribute("msg", "注册成功,快去邮箱激活吧");
			return "/msg.jsp";// 返回要BaseServlet做转发处理
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "邮件发送失败，请确认邮箱正确");
			return "/msg.jsp";// 返回要BaseServlet做转发处理
		}

	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		// 获取表单数据
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// 因为我们在注册的时候把密码加密了,所以在登录的时候还是要把密码加密,不然和数据库里的密码对不上了
		password = MD5Utils.md5(password);
		UserService us = new UserServiceImpl();
		User user = us.findUserByUsernameAndPwd(name, password);
		// 检验验证码
		String number1 = request.getParameter("number");
		HttpSession session = request.getSession();
		String number2 = (String) session.getAttribute("number");
		if (!number1.equals(number2)) {
			request.setAttribute("number_error", "验证码错误");
			return "/login.jsp";
		}
		// 读取用户输入数据
		if (user == null) {
			request.setAttribute("inform", "用户名或密码错误");
			return "/login.jsp";
		}
		// 登录成功
		else {
			Log log =new Log();
			log.setName(name);
			log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			log.setLogtype("登录");
			log.setDetail("用户登录");
			log.setTime(DateUtils.getSystemTime());
			log.setIp(IpUtils.getIpAddress(request));
			LogService ls = new LogServiceImpl();
			ls.add(log);
			
			// 接收数据
			Map<String, String[]> map = request.getParameterMap();
			try {
				
				// 封装数据
				BeanUtils.populate(user, map);
				// 第二个用户登录后把之前的session给消除
				request.getSession().invalidate();
				// 判断用户是否在map集合中，存在，销毁session，防止不同游览器登录同一账户不能踢出的问题
				@SuppressWarnings("unchecked")
				Map<User, HttpSession> usermap = (Map<User, HttpSession>) getServletContext().getAttribute("usermap");
				// 判断用户是否已经在map集合中'
				if (usermap.containsKey(user)) {
					// 说用map中有这个用户.
					HttpSession session1 = usermap.get(user);
					// 将这个session销毁.
					session1.invalidate();
				}
				// 如果数据库里有这个用户,就把它放入session中
				// 使用监听器：HttpSessionBandingListener作用在JavaBean上的监听器
				request.getSession().setAttribute("user", user);
				// 所有人的信息都能看见，所以信息存在servletContext域中
				ServletContext application = getServletContext();
				String sourceMessage = "";
				if (null != application.getAttribute("message")) {
					sourceMessage = application.getAttribute("message").toString();
				}
				sourceMessage += "系统公告：<font color='gray'>" + user.getNickname() + "走进了聊天室！</font><br>";
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
	 * 用户激活
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
				log.setLogtype("添加");
				log.setDetail("创建用户");
				log.setTime(DateUtils.getSystemTime());
				log.setIp(IpUtils.getIpAddress(request));
				LogService ls = new LogServiceImpl();
				ls.add(log);
				return "/login.jsp";// 返回要BaseServlet做转发处理
				}
				else {
				request.setAttribute("msg", "验证码错误,请重回邮件激活！");
				return "/active.jsp";
				}
			} else {
				request.setAttribute("msg", "验证码错误,请重回邮件激活！");
				return "/active.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "激活失败");
			return "/active.jsp";// 返回要BaseServlet做转发处理
		}
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset:utf-8");
		// 封装数据
		try {
			String name = request.getParameter("name");
			String nickname = request.getParameter("nickname");
			String sex = request.getParameter("sex");
			String age = request.getParameter("age");
			String profile = request.getParameter("profile");
			String newProfile=new String(profile.getBytes("ISO-8859-1"),"UTF-8");
			//调整业务逻辑
			UserService us = new UserServiceImpl();
			us.updateInformation(name, nickname,sex,age,newProfile);
			Log log =new Log();
			log.setName(name);
			log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			log.setLogtype("修改");
			log.setDetail("修改用户信息");
			log.setTime(DateUtils.getSystemTime());
			log.setIp(IpUtils.getIpAddress(request));
			LogService ls = new LogServiceImpl();
			ls.add(log);
			request.setAttribute("message", "信息修改成功");
			return "/config.jsp";// 返回要BaseServlet做转发处理
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "信息修改失败，请按照要求修改");
			return "/msg.jsp";// 返回要BaseServlet做转发处理
		}
		
	}
	
	/**
	 * 修改用户密码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String changePassword(HttpServletRequest request,HttpServletResponse response) throws  Exception{
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		//封装数据
		try {
			String name = request.getParameter("name");
			String password=request.getParameter("oldpass");
			password = MD5Utils.md5(password);
			UserService us = new UserServiceImpl();
			User users = us.findUserByUsernameAndPwd(name,password);
			if(users==null) {
				request.setAttribute("error", "原始密码错误");
				return "/config.jsp";
			}
			String newpassword = request.getParameter("newpass");
			String newpasswordconfirm = request.getParameter("newpassconfirm");
			if(newpassword.length()!=newpasswordconfirm.length()) {
				request.setAttribute("error", "俩次密码不一致");
				return "/config.jsp";// 返回要BaseServlet做转发处理
			}
			else {
				password=MD5Utils.md5(newpassword);
				us.updatePassword(name, password);
				Log log =new Log();
				log.setName(name);
				log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
				log.setLogtype("修改");
				log.setDetail("修改用户密码");
				log.setTime(DateUtils.getSystemTime());
				log.setIp(IpUtils.getIpAddress(request));
				LogService ls = new LogServiceImpl();
				ls.add(log);
				request.setAttribute("message", "信息修改成功");
				return "/config.jsp";
			}
		}catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "信息修改失败，请按照要求修改");
			return "/msg.jsp";// 返回要BaseServlet做转发处理
		}
	}
	
	/**
	 * 更新用户头像
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
	        System.out.println("servlet获取的img数据为:"+users.getProfilehead());
	        String profilehead=users.getProfilehead();
			UserService us = new UserServiceImpl();
			User user = us.updatePicture(name, profilehead);
            if(user!=null){
            	Log log =new Log();
				log.setName(name);
				log.setLogid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
				log.setLogtype("修改");
				log.setDetail("修改用户头像");
				log.setTime(DateUtils.getSystemTime());
				log.setIp(IpUtils.getIpAddress(request));
				LogService ls = new LogServiceImpl();
				ls.add(log);
				request.setAttribute("message", "头像更新成功!");
				return "/config.jsp";
            }else{
            	request.setAttribute("error", "头像更新失败!");
            	return "/config.jsp";
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			request.setAttribute("error", "出现问题了!");
			return "/config.jsp";
		}
	}

}
