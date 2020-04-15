package dao;

import java.sql.SQLException;
import java.util.List;

import entity.Log;

public interface LogDao {
	//�����־
	public void add(Log log);
	//�鿴��־
	public List<Log> selectByName(String name) throws SQLException;
	//������־����
	public int getNum(String name);
}
