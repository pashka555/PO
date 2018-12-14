package hello;

import java.io.Serializable;

public class Message implements Serializable{
	
    private final long id;
    private SessionCookieObject UserAUTH;
    private final String content;

    public Message(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
	

}
