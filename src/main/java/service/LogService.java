package service;

import java.util.List;

import entity.Log;

public interface LogService {
	//�����־
	public void add(Log log) throws Exception;
	//��ʾ��־
	public List<Log> getLogs(String name) throws Exception;
	//������־����
	public int getNum(String name) throws Exception;
}
