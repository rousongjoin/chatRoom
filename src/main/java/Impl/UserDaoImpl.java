package Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import dao.UserDao;
import entity.User;
import util.DbUtiles;

public class UserDaoImpl implements UserDao {
	Connection conn = null;

	// 添加用户
	public void add(User user) {
		try {
			conn = DbUtiles.getConnection();
			String sql = "insert into users values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getSex());
			ps.setInt(5, user.getType());
			ps.setString(6, user.getE_mail());
			ps.setString(7, user.getCode());
			ps.setString(8, user.getCreatetime());
			ps.setInt(9, user.getState());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtiles.closeConnection(conn);
		}
	}

	/**
	 * 用户注册后,通过激活码查询数据库中有没有该用户
	 */
	public User findUserByCode(String code) throws Exception {
		String sql = "select * from users where code=?";
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}

	/**
	 * 修改用户的state状态为1,则表示已激活,状态初识为0,表示为激活
	 * 
	 * @throws SQLException
	 * 
	 */
	public void update(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		String sql = "update users set id=?,name=?,password=?,sex=?,type=?,e_mail=?,code=?,createtime=? where state=? ";
		qr.update(sql, user.getId(), user.getName(), user.getPassword(), user.getSex(), user.getType(),
				user.getE_mail(), null, user.getCode(), user.getCreatetime(), user.getState());
	}

	/**
	 * 用户登录,通过查询数据库中有没有该用户
	 */
	public User findUserByUsernameAndPwd(String username, String password) throws Exception {
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		String sql = "select * from users where name=? and password=?";
		return qr.query(sql, new BeanHandler<User>(User.class), username, password);
	}
}
