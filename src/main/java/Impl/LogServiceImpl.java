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

	public List<Log> getLogs(String name, int page, int pageSize) throws Exception {
		int start=1;
		int end =pageSize;
		if(page != 1) {
            start = pageSize * (page - 1) + 1;
        }
		LogDao ld = new LogDaoImpl();
		return ld.selectByName(name,start,end);
	}

	public int getNum(String name,int pageSize) throws Exception {
		LogDao ld = new LogDaoImpl();
		int pageCount = ld.getNum(name);
		return pageCount % pageSize == 0 ? pageCount/pageSize : pageCount/pageSize + 1;
	}

}
