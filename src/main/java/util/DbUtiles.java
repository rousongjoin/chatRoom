package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DbUtiles {
	static private String className;
	static private String url;
	static private String user;
	static private String password;
	static private int initialSize;
	static private int maxActive;
	static private BasicDataSource bds;

	static {
		bds = new BasicDataSource();
		// ��ȡ�ļ���Ϣ
		Properties pp = new Properties();
		InputStream is = DbUtiles.class.getClassLoader().getResourceAsStream("db.properties");
		try {
			// ��ȡ���Ӳ���
			pp.load(is);
			className = pp.getProperty("jdbc.className");
			url = pp.getProperty("jdbc.url");
			user = pp.getProperty("jdbc.user");
			password = pp.getProperty("jdbc.password");
			initialSize = Integer.parseInt(pp.getProperty("InitialSize"));
			maxActive = Integer.parseInt(pp.getProperty("MaxActive"));
			// ���ò���
			bds.setDriverClassName(className);
			bds.setUrl(url);
			bds.setUsername(user);
			bds.setPassword(password);
			bds.setInitialSize(initialSize);
			bds.setMaxActive(maxActive);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = bds.getConnection();
		return conn;
	}

	// �Ͽ����ӷ���
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static DataSource getBasicDataSource(){
		return  bds;
	}


}
