package service;

import entity.User;

public interface UserService {
	//ע���û����ͼ����Ҽ�
	public void add(User user) throws Exception;
	//����û��Ƿ񼤻�
	public User findUserByCode(String code) throws Exception;
	//ͨ�����ֺ��������
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;
	

}
