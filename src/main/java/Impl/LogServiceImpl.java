package Impl;

import java.util.List;

import dao.LogDao;
import entity.Log;
import service.LogService;

public class LogServiceImpl implements LogService{

	public void add(Log log) throws Exception {
		LogDao ld = new LogDaoImpl();
		ld.add(log);
	}

	public List<Log> getLogs(String name) throws Exception {
		LogDao ld = new LogDaoImpl();
		List<Log> logs=ld.selectByName(name);
		return logs;
	}

	public int getNum(String name) throws Exception {
		LogDao ld = new LogDaoImpl();
		int count =ld.getNum(name);
		return count;
	}

}
