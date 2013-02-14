package chatter.model;

import java.sql.Timestamp;

public class Message 
{
	private Timestamp timeStamp;
	private String postingUser;
	private String content;

	public Message()
	{
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPostingUser() {
		return postingUser;
	}

	public void setPostingUser(String postingUser) {
		this.postingUser = postingUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
