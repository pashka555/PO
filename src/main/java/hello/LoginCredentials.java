package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginCredentials {
	String login;
	String pass;
	
	@JsonCreator
	public LoginCredentials(@JsonProperty("login") String login, @JsonProperty("pass") String pass) {
		super();
		this.login = login;
		this.pass = pass;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

}
