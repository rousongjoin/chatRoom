package entity;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * 
 * ��ţ�id��String�� �û�����name��String�� ���룺password��String�� �Ա�sex��String��
 * ���type��int�� ���䣺e-mail��String�� �ʼ������룺code��String�� ���գ�createtime��String��
 * ״̬��state��int��
 *
 */

/**
 * ���������ǳƣ�nickname ��String��
 * ͷ��profilehead (String)
 * ��飺profile ��String��
 * ���䣺age (String)
 * ����ʱ�䣺firsttime ��String��
 * ����¼ʱ�䣺lasttime (String)
 *
 */
public class User implements HttpSessionBindingListener {
	private String nickname;
	private String profilehead;
	private String profile;
	private String age;
	private String firsttime;
	private String lasttime;
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfilehead() {
		return profilehead;
	}

	public void setProfilehead(String profilehead) {
		this.profilehead = profilehead;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getFirsttime() {
		return firsttime;
	}

	public void setFirsttime(String firsttime) {
		this.firsttime = firsttime;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	private String id;
	private String name;
	private String password;
	private String sex;
	private int type;
	private String e_mail;
	private String code;
	private String createtime;
	private int state;

	//��дhashcode�����ж��Ƿ�Ϊͬһ����
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getType() {
		return type;
	}

	public void setType(int i) {
		this.type = i;
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}





	@Override
	public String toString() {
		return "User [nickname=" + nickname + ", profilehead=" + profilehead + ", profile=" + profile + ", age=" + age
				+ ", firsttime=" + firsttime + ", lasttime=" + lasttime + ", id=" + id + ", name=" + name
				+ ", password=" + password + ", sex=" + sex + ", type=" + type + ", e_mail=" + e_mail + ", code=" + code
				+ ", createtime=" + createtime + ", state=" + state + "]";
	}

	/**
	 * ��JavaBean��session�� session.setAttribute������
	 */
	public void valueBound(HttpSessionBindingEvent event) {
		// ��̨�������
		System.out.println("������...");
		// ͨ���¼��������¼�Դ����
		HttpSession session = event.getSession();
		// ��servletContext�л����Ա�б��map����
		@SuppressWarnings("unchecked")
		Map<User, HttpSession> usermap = (Map<User, HttpSession>)session.getServletContext().getAttribute("usermap");
		// ���û�����Ͷ�Ӧ��session���뵽map������
		usermap.put(this, session);
	}

	// java������session�Ӵ���
	public void valueUnbound(HttpSessionBindingEvent event) {
		// ��̨����˳�
		System.out.println("�˳���...");
		// ͨ���¼��������¼�Դ����
		HttpSession session = event.getSession();
		// ��servletContext�л����Ա�б��map����
		@SuppressWarnings("unchecked")
		Map<User, HttpSession> usermap = (Map<User, HttpSession>) session.getServletContext().getAttribute("usermap");
		// ��map���Ƴ�
		usermap.remove(this, session);

	}

}
