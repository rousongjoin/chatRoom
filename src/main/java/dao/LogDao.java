package dao;

import java.sql.SQLException;
import java.util.List;

import entity.Log;

public interface LogDao {
	//添加日志
	public void add(Log log);
	//查看日志
	public List<Log> selectByName(String name) throws SQLException;
	//返回日志个数
	public int getNum(String name);
}
