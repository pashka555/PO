package hello;

import java.io.Serializable;

public class RegistrationData implements Serializable {
	String login;
	String password;
	String desiredNick;
	
	public RegistrationData(String login, String password, String desiredNick) {
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
