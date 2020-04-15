package service;

import entity.User;

public interface UserService {
	//注册用户发送激活邮件
	public void add(User user) throws Exception;
	//检查用户是否激活
	public User findUserByCode(String code) throws Exception;
	//返回通过密码和姓名查找的用户信息
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;
	//修改用户信息
	public User updateInformation(String name,String nickname,String sex,String age,String profile) throws Exception;
	//返回通过姓名查找的用户信息
	public User findUserByName(String name) throws Exception;
	//修改用户密码
	public User updatePassword(String name,String password) throws Exception;
	//修改用户头像
	public User updatePicture(String name,String profilehead)throws Exception;
	

}
