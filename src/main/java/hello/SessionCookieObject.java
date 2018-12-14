package hello;

public class SessionCookieObject {
	String nickname;
	String hashedSalts;
	
	public SessionCookieObject(String nickname, String hashedSalts) {
		super();
		this.nickname = nickname;
		this.hashedSalts = hashedSalts;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHashedSalts() {
		return hashedSalts;
	}
	public void setHashedSalts(String hashedSalts) {
		this.hashedSalts = hashedSalts;
	}
	
}
