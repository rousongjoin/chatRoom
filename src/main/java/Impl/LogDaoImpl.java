package Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import dao.LogDao;
import entity.Log;
import util.DbUtiles;

public class LogDaoImpl implements LogDao{
	Connection conn = null;

	public void add(Log log) {
		try {
			conn = DbUtiles.getConnection();
			String sql = "insert into logs values(?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, log.getLogid());
			ps.setString(2, log.getName());
			ps.setString(3, log.getTime());
			ps.setString(4, log.getLogtype());
			ps.setString(5, log.getDetail());
			ps.setString(6, log.getIp());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtiles.closeConnection(conn);
		}
	}

	public List<Log> selectByName(String name ,int offset,int limit) throws SQLException {
		String sql = "select name, time, logtype, detail, ip from logs where "
				+ "name=? limit ?,?";
		QueryRunner qr = new QueryRunner(DbUtiles.getBasicDataSource());
		List<Log> logs = qr.query(sql, new BeanListHandler<Log>(Log.class), name, offset, limit);
		return logs;
	}

	public int getNum(String name) {
		int number = 0;
		try {
		conn = DbUtiles.getConnection();
		String sql = "select * from logs where name=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			number++;
		}
		rs.close();
		ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtiles.closeConnection(conn);
		}
		return number;
	}
	
	

}
