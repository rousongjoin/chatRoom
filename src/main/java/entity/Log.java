package entity;

/**
 * 日志编号:logid （String）
		用户名：name (String)
		操作时间:time (String)
		操作类型：logtype (String)
		详情：detail （String）
		ip地址：ip （String）
 * @author pc
 *
 */

public class Log {
	private String logid;
	private String name;
	private String time;
	private String logtype;
	private String detail;
	private String ip;
	public String getLogid() {
		return logid;
	}
	public void setLogid(String id) {
		this.logid = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLogtype() {
		return logtype;
	}
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "Log [logid=" + logid + ", name=" + name + ", time=" + time + ", logtype=" + logtype + ", detail=" + detail
				+ ", ip=" + ip + "]";
	}
	
	

}
