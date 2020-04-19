package service;

import java.util.List;

import entity.Log;

public interface LogService {
	//添加日志
	public void add(Log log) throws Exception;
	//显示日志
	public List<Log> getLogs(String name,int page, int pageSize) throws Exception;
	//返回日志个数
	public int getNum(String name,int pageSize) throws Exception;
}
