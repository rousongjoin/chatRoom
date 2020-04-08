package test;

import Impl.UserServiceImpl;
import entity.User;
import service.UserService;
import util.MD5Utils;

public class testAdd {
	public static void main(String[] args) throws Exception {
		User user=new User();
		user.setName("admin");
		user.setPassword(MD5Utils.md5("admin"));
		user.setState(1);
		user.setType(1);
		UserService us=new UserServiceImpl() ;
		us.add(user);
		System.out.println(1);
		
	}

}
