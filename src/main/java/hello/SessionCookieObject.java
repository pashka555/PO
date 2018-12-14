package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionCookieObject {
	//String nickname;
	final String hashedSalts;
	
	@JsonCreator
	public SessionCookieObject(@JsonProperty("hashedSalts") String hashedSalts) {
		super();
		//this.nickname = nickname;
		this.hashedSalts = hashedSalts;
	}
	
//	public String getNickname() {
//		return nickname;
//	}
//	public void setNickname(String nickname) {
//		this.nickname = nickname;
//	}
	public String getHashedSalts() {
		return hashedSalts;
	}
//	public void setHashedSalts(String hashedSalts) {
//		this.hashedSalts = hashedSalts;
//	}
//	
}
