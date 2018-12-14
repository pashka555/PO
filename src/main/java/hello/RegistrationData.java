package hello;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationData implements Serializable {
	String login;
	String password;
	String desiredNick;
	
	@JsonCreator
	public RegistrationData(@JsonProperty("login") String login, @JsonProperty("password") String password,
			@JsonProperty("nick") String desiredNick) {
		super();
		this.login = login;
		this.password = password;
		this.desiredNick = desiredNick;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDesiredNick() {
		return desiredNick;
	}
	public void setDesiredNick(String desiredNick) {
		this.desiredNick = desiredNick;
	}
}
