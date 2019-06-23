package lnu.postoffice.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import lnu.postoffice.model.RegistrationData;
import lnu.postoffice.model.SessionCookieObject;
import lnu.postoffice.model.LoginCredentials;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
	
	private static final Random RANDOM = new SecureRandom();
	private static final int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 256;	
	
	
	public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        return bytes;
    }

	public String byteToString(byte[] input) {
        return Base64.encodeBase64String(input);
    }

	public byte[] getHashWithSalt(String input, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] hashedBytes = digest.digest(stringToByte(input));
        return hashedBytes;
    }
	
	public byte[] stringToByte(String input) {
        if (Base64.isBase64(input)) {
            return Base64.decodeBase64(input);

        } else {
            return Base64.encodeBase64(input.getBytes());
        }
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
						
			String hashedPass = byteToString(getHashWithSalt(loginData.getPass(),stringToByte(userSalt)));
			
			//TODO check if session is there, and if is valid
			
			if(hashedPass.equals(rs.getString(4))) {
				PreparedStatement pickSessionStatement =
						connection.prepareStatement("INSERT INTO sessions (nickname, salt) VALUES (?,?)");
				sessionSalt = byteToString(generateSalt());
				pickSessionStatement.setString(1, rs.getString(2));
				pickSessionStatement.setString(2, sessionSalt);
				pickSessionStatement.execute();
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
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
					connection.prepareStatement("INSERT INTO users (desirednick,login,salt,hash) VALUES (?,?,?,?)");
			
    		byte[] userSalt = generateSalt();
    		
    		//Database constraints handle uniquity of login and nickname
			statement.setString(1, register.getDesiredNick());
			statement.setString(2, register.getLogin());
			
			statement.setString(3, byteToString(userSalt));
			
			String hashedPass = byteToString(getHashWithSalt(register.getPassword(),userSalt));
			 
			statement.setString(4, hashedPass);
			
			statement.execute();
			return (Authenticate(new LoginCredentials(register.getLogin(),register.getPassword())));
		} catch (SQLException e) {
			//System.out.println("Connection failure.");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	
		return null;
	}
	
	}
