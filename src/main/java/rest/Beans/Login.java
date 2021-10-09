package rest.Beans;

public class Login {

	private String userName;
	private String userPassword;
	private String userRole;
	private String flatNumber;
	
	
	public Login(String userName, String userPassword, String userRole, String flatNumber) {
		super();
		this.userName = userName;
		this.userPassword = userPassword;
		this.userRole = userRole;
		this.flatNumber = flatNumber;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
