package hello;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthAndMessage implements Serializable {

	//private final long id;
    //private final String nickname;
    private final SessionCookieObject UserAUTH;
    private final String content;
    
    @JsonCreator
    public AuthAndMessage(@JsonProperty("userAUTH") final SessionCookieObject userAuth , @JsonProperty("content") final String content ) {
    	UserAUTH = userAuth;
    	this.content = content;
    }
    
//	public AuthAndMessage(SessionCookieObject userAUTH, String content) {
//		super();
//		UserAUTH = userAUTH;
//		this.content = content;
//	}

	public SessionCookieObject getUserAUTH() {
		return UserAUTH;
	}
	
	public String getContent() {
		return content;
	}

    	
}
