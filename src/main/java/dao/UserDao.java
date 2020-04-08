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
	
	//通过验证码找用户
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;


}
