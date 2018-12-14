package hello;

import java.io.Serializable;

public class Message implements Serializable{
	
    //private final long id;
    private final String nickname;
    //private SessionCookieObject UserAUTH;
    private final String content;

    public Message(String content, String nickname) {
        //this.id = id;
        this.content = content;
        this.nickname = nickname;
    }

//	public long getId() {
//		return id;
//	}

	public String getNickname() {
		return nickname;
	}

	public String getContent() {
		return content;
	}

}
