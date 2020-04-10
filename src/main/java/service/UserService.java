package service;

import entity.User;

public interface UserService {
	//ע���û����ͼ����ʼ�
	public void add(User user) throws Exception;
	//����û��Ƿ񼤻�
	public User findUserByCode(String code) throws Exception;
	//ͨ�����ֺ��������
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;
	//�޸��û���Ϣ
	public User updateInformation(String name,String sex) throws Exception;
	//ͨ���û�������
	public User findUserByName(String name) throws Exception;
	//�޸��û�����
	public User updatePassword(String name,String password) throws Exception;
	

}
