package dao;

import java.sql.SQLException;

import entity.User;

public interface UserDao {
	// 添加用户
	public void add(User user);
	
	//通过验证码找用户
	public User findUserByCode(String code) throws Exception;
	
	//修改验证状态
	public void update(User user) throws SQLException;
	
	//通过账号信息找用户
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;
	
	//修改用户信息
	public void updateInformation(User user) throws Exception;
	
	//获得用户
	public User getUser(String name)throws Exception;
	
	//修改用户密码
	public void updatePassword(User user) throws Exception;
}
