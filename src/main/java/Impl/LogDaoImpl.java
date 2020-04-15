package Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	public List<Log> selectByName(String name) throws SQLException {
		List<Log> logs = new ArrayList<Log>();
		try {
		conn = DbUtiles.getConnection();
		String sql = "select * from logs where name=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String logname = rs.getString("name");
			String time = rs.getString("time");
			String logtype = rs.getString("logtype");
			String detail = rs.getString("detail");
			String ip = rs.getString("ip");
			Log log = new Log();
			log.setName(logname);
			log.setTime(time);
			log.setLogtype(logtype);
			log.setDetail(detail);
			log.setIp(ip);
			logs.add(log);
		}
		rs.close();
		ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtiles.closeConnection(conn);
		}
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
