package service;

import entity.User;

public interface UserService {
	//ע���û����ͼ����ʼ�
	public void add(User user) throws Exception;
	//����û��Ƿ񼤻�
	public User findUserByCode(String code) throws Exception;
	//����ͨ��������������ҵ��û���Ϣ
	public User findUserByUsernameAndPwd(String username, String password) throws Exception;
	//�޸��û���Ϣ
	public User updateInformation(String name,String nickname,String sex,String age,String profile) throws Exception;
	//����ͨ���������ҵ��û���Ϣ
	public User findUserByName(String name) throws Exception;
	//�޸��û�����
	public User updatePassword(String name,String password) throws Exception;
	//�޸��û�ͷ��
	public User updatePicture(String name,String profilehead)throws Exception;
	

}
