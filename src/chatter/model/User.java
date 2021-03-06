package chatter.model;

//JavaBean dto representing a User. Model of MVC
public class User //BEAN
{

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean friend;	//Is this user a friend of the currently logged-in user?
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isFriend() {
		return friend;
	}
	public void setFriend(boolean friend) {
		this.friend = friend;
	}
		
	
}
