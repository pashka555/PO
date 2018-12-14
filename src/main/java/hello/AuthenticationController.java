package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
	
	@RequestMapping(value="/auth", method = RequestMethod.POST)
	public SessionCookieObject Authenticate(@RequestParam(value = "login") String login, 
			@RequestParam(value="hash") String pass) {
		//TODO salt+hash the received password then check the DB, 
		//if present, generate a session token in DB, if not, return an error message.
		//Can be KISSed into separate methods, of course
		return null;
		
	}
	
	@RequestMapping(value="/sessionCheck", method = RequestMethod.POST)
	public boolean isSessionValid(SessionCookieObject session) {
		//TODO check if last usage of session cookie is not way past 10 minutes with a pair of 
		//SessionCookie and a date-time from DB
		return true;
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public SessionCookieObject Register(RegistrationData register) {
		//TODO check if nick, and login are taken, generate salt
		//TODO salt+hash the password, add user to DB and immediately generate a session
		return null;
	}
	
}
