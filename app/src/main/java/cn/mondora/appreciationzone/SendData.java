package cn.mondora.appreciationzone;

import java.io.Serializable;

public class SendData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String userId;
	private String sessionid;
	private String version;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
