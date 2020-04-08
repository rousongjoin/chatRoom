package service;

import entity.User;

public interface UserService {
	//注册用户发送激活右键
	public void add(User user) throws Exception;
	//检查用户是否激活
	public User findUserByCode(String code) throws Exception;
	//通过名字和密码查找
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;
	

}
