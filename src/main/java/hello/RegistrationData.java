package hello;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationData implements Serializable {
	private String login;
	private String password;
	private String desiredNick;
	
	@JsonCreator
	public RegistrationData(@JsonProperty("login") String login, @JsonProperty("password") String password, @JsonProperty("desiredNick") String desiredNick) {
		super();
		this.login = login;
		this.password = password;
		this.desiredNick = desiredNick;
	}
	
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDesiredNick(String desiredNick) {
		this.desiredNick = desiredNick;
	}


	public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}


	public String getDesiredNick() {
		return desiredNick;
	}
	
	
}
