package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	
	//TODO make DB for it --- DONE

    @RequestMapping(value="/send", method = RequestMethod.POST)
    public Message SendMessage( Message message) {
    		//TODO authenticate session with one DB and send message to another DB. 
    		//for now is just a dummy to fiddle with POST-requests
    		return(message);
    }
    
    @RequestMapping("/load")
    public void LoadMessages() {
    	//TODO limit to last 50 messages, change return type and return the query result
    	try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ChatServiceDB", "postgres", "12p23p34")) {

			
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM messages");
			

		} /*catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBC driver not found.");
			e.printStackTrace();
		}*/ catch (SQLException e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
    }
}
