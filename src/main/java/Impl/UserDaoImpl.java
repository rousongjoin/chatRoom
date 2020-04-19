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

	// ����û�
	public void add(User user) {
		try {
			conn = DbUtiles.getConnection();
			String sql = "insert into users values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			ps.setString(10, user.getNickname());
			ps.setString(11, user.getProfilehead());
			ps.setString(12, user.getProfile());
			ps.setString(13, user.getAge());
			ps.setString(14, user.getFirsttime());
			ps.setString(15, user.getLasttime());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtiles.closeConnection(conn);
		}
	}

	/**
	 * �û�ע���,ͨ���������ѯ���ݿ�����û�и��û�
	 */
	public User findUserByCode(String code) throws Exception {
		String sql = "select * from users where code=?";
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class), code);
	}

	/**
	 * �޸��û���state״̬Ϊ1,���ʾ�Ѽ���,״̬��ʶΪ0,��ʾΪ����
	 * 
	 * @throws SQLException
	 * 
	 */
	public void update(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		String sql = "update users set id=?,password=?,sex=?,type=?,e_mail=?,code=?,createtime=? ,state=?,nickname=?,profilehead=?,profile=?,age=?,firsttime=?,lasttime=? where name=? ";
		qr.update(sql, user.getId() , user.getPassword(), user.getSex(), user.getType(),
				user.getE_mail(), null, user.getCreatetime(), user.getState(),user.getNickname()
				,user.getProfilehead(),user.getProfile(),user.getAge(),user.getFirsttime(),user.getLasttime(),user.getName());
	}

	/**
	 * �û���¼,ͨ����ѯ���ݿ�����û�и��û�
	 */
	public User findUserByUsernameAndPwd(String username, String password) throws Exception {
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		String sql = "select * from users where name=? and password=?";
		return qr.query(sql, new BeanHandler<User>(User.class), username, password);
	}

	
	/**
	 * ͨ���û�����ȡ��Ϣ
	 */
	public User getUser(String name) throws Exception{
		String sql = "select * from users where name=?";
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class), name);

	}

	public User findUserByNiackname(String nickname) throws Exception {
		String sql = "select * from users where nickname=?";
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		return qr.query(sql, new BeanHandler<User>(User.class), nickname);
	}
	

}
