package Impl;

import dao.UserDao;
import entity.User;
import service.UserService;
import util.MailUtils;

public class UserServiceImpl implements UserService {
	
	public void add(User user) throws Exception {
		UserDao ud = new UserDaoImpl();
		ud.add(user);
		// 发送激活邮件
			// email:收件人地址
				// emailMsg:邮件的内容
		String code = user.getCode();
		String emailMsg = "欢迎您注册成为我们的会员,你的验证码为："+code+
				"<a href='http://localhost:8080/chatRoom/UserServlet?method=activeUI&code="
				+ user.getCode() + "'>点击链接激活</a>";
		MailUtils.sendMail(user.getE_mail(), emailMsg);// 这个是一个工具类
		System.out.println("邮件发送成功");
	}

	public User findUserByCode(String code) throws Exception {
        UserDao ud=new UserDaoImpl();
        User user=ud.findUserByCode(code);
		if(user==null) {
			//说明该用户没有注册成功;返回null,在UserServlet中做判断
			return null;
		}
		user.setState(1);//如果注册成功了就把用户的状态从初始值0改为1,说明已经激活了
		System.out.println("已传递激活数据");
		ud.update(user);
		return user;
		
	}

	public User findUserByUsernameAndPwd(String username, String password) throws Exception {
		UserDao ud=new UserDaoImpl();
		User user=ud.findUserByUsernameAndPwd(username, password);
		if(user==null) {
			//说明用户不存在数据库中，返回null，在UserServlet中做判断
			return null;
		}
		return user;
	}

}
