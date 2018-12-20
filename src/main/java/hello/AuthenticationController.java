package hello;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
	
	private static final Random RANDOM = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;	
	
	
	public static byte[] getNextSalt() {
	    byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return salt;
	  }
	
	
	@RequestMapping(value="/auth", method = RequestMethod.POST)
	public SessionCookieObject Authenticate(LoginCredentials loginData) {
		String sessionSalt = "error";
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatServiceDB", "postgres", "12p23p34")) {
			PreparedStatement statement = 
					connection.prepareStatement("SELECT * FROM users WHERE login = ?");
			
			statement.setString(1, loginData.login);
			
			ResultSet rs = statement.executeQuery();
			
			rs.next();
			
			String userSalt = rs.getString(3);
			
			String hashedPass = loginData.getPass() + userSalt;
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(hashedPass.getBytes("UTF-8"));
			hashedPass = new String(messageDigest.digest(), "UTF-8");
			
			
			
			if(hashedPass == rs.getString(4)) {
				PreparedStatement pickSessionStatement =
						connection.prepareStatement("INSERT INTO sessions (nick, hash) VALUES (?,?,NOW())");
				sessionSalt = new String(getNextSalt(),"UTF-8");
				pickSessionStatement.setString(1, rs.getString(2));
				pickSessionStatement.setString(2, sessionSalt);
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return (new SessionCookieObject(sessionSalt));
		
	}
	
	@RequestMapping(value="/sessionCheck", method = RequestMethod.POST)
	public boolean isSessionValid(SessionCookieObject session) {
		//TODO check if last usage of session cookie is not way past 10 minutes with a pair of 
		//SessionCookie and a date-time from DB
		return true;
	}

	@RequestMapping(value="/register", method = RequestMethod.POST)
	public SessionCookieObject Register(RegistrationData register) {
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatServiceDB", "postgres", "12p23p34")) {
    		PreparedStatement statement = 
					connection.prepareStatement("INSERT INTO users (desirednick,login,salt,password) VALUES (?,?,?,?)");
			
    		byte[] userSalt = getNextSalt();
    		
    		//Database constraints handle uniquity of login and nickname
			statement.setString(1, register.getDesiredNick());
			statement.setString(2, register.getLogin());
			
			statement.setString(3, new String(userSalt, "UTF-8"));
			
			
			String hashedPass = register.getPassword() + new String(userSalt, "UTF-8");
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(hashedPass.getBytes("UTF-8"));
			hashedPass = new String(messageDigest.digest(), "UTF-8");
			statement.setString(4, hashedPass);
			
			statement.execute();			
		} catch (UnsupportedEncodingException e) {
			//System.out.println("PostgreSQL JDBC driver not found.");
			e.printStackTrace();
		} catch (SQLException e) {
			//System.out.println("Connection failure.");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	
		return (Authenticate(new LoginCredentials(register.getLogin(),register.getPassword())));
	}
	
}
