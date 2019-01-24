package hello;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.postgresql.core.NativeQuery;
import org.postgresql.core.Parser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	//TODO make DB for it --- DONE
	@RequestMapping(value="/test")
	public AuthAndMessage testJSON() {
		return (new AuthAndMessage(new SessionCookieObject("asd"),"asd"));
	}

    @RequestMapping(value="/send", method = RequestMethod.POST)
    public String SendMessage(@RequestBody AuthAndMessage message) {
    	System.out.println("Received an AuthAndMessage request");
    	//System.out.println(message.getContent());
    	//System.out.println(message.getUserAUTH().hashedSalts);
    	//boolean success = false;
    	try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatServiceDB", "postgres", "12p23p34")) {
			//get nickname by token
    		
    		//TODO check if token is valid
    		
    		PreparedStatement query = 
					connection.prepareStatement("SELECT nickname FROM sessions WHERE \"hash\" = ?");
    		
    		query.setString(1, message.getUserAUTH().getHashedSalts());
    					
			ResultSet rs = query.executeQuery();
			
			rs.next();
			
			String nickname = rs.getString(1);
			
			//post message with a non-null nickname
			if(nickname != null) {
			Message prepMessage = new Message(message.getContent(),nickname);
    		
    		PreparedStatement statement = 
					connection.prepareStatement("INSERT INTO messages (nickname,message,posted_on) VALUES (?,?,NOW()) ");
			
			statement.setString(1, prepMessage.getNickname());
			statement.setString(2, prepMessage.getContent());
			statement.execute();
			return("Success");
			} else {
				return("Session is not valid. Please log in again.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return("An error has occured while processing the message");
		}    	
    }
    
    @RequestMapping("/load")
    public List<Message> LoadMessages() {
    	System.out.println("Received a load request");
    	List<Message> result = new ArrayList<Message>();
    	//TODO limit to last 50 messages, change return type and return the query result
    	try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatServiceDB", "postgres", "12p23p34")) {

			
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT nickname, message FROM messages ORDER BY posted_on DESC LIMIT 50; ");
			
			while(resultSet.next()) {
				result.add(new Message(resultSet.getString(2),resultSet.getString(1)));			
			}
		} catch (SQLException e) {
			//System.out.println("Connection failure.");
			e.printStackTrace();
		}
    	return result;
    }
}
