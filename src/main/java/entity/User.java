package entity;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * 
 * 编号：id（String） 用户名：name（String） 密码：password（String） 性别：sex（String）
 * 类别：type（int） 邮箱：e-mail（String） 邮件激活码：code（String） 生日：createtime（String）
 * 状态：state（int）
 *
 */
public class User implements HttpSessionBindingListener {
	private String id;
	private String name;
	private String password;
	private String sex;
	private int type;
	private String e_mail;
	private String code;
	private String createtime;
	private int state;

	//重写hashcode用来判断是非为同一对象
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
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", sex=" + sex + ", type=" + type
				+ ", e_mail=" + e_mail + ", code=" + code + ", createtime=" + createtime + ", state=" + state + "]";
	}

	/**
	 * 将JavaBean与session绑定 session.setAttribute（）；
	 */
	public void valueBound(HttpSessionBindingEvent event) {
		// 后台输出进入
		System.out.println("进入了...");
		// 通过事件对象获得事件源对象
		HttpSession session = event.getSession();
		// 从servletContext中获得人员列表的map集合
		@SuppressWarnings("unchecked")
		Map<User, HttpSession> usermap = (Map<User, HttpSession>)session.getServletContext().getAttribute("usermap");
		// 将用户对象和对应的session存入到map集合中
		usermap.put(this, session);
	}

	// java对象与session接触绑定
	public void valueUnbound(HttpSessionBindingEvent event) {
		// 后台输出退出
		System.out.println("退出了...");
		// 通过事件对象获得事件源对象
		HttpSession session = event.getSession();
		// 从servletContext中获得人员列表的map集合
		@SuppressWarnings("unchecked")
		Map<User, HttpSession> usermap = (Map<User, HttpSession>) session.getServletContext().getAttribute("usermap");
		// 从map中移除
		usermap.remove(this, session);

	}

}
