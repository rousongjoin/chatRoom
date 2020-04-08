package test;

import java.sql.Connection;

import util.DbUtiles;

public class testConnection {
	public static void main(String[] args) {
		try {
			Connection conn = DbUtiles.getConnection();
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
