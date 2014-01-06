package wm.edu.cs420.Data;






/**
 * @author himanshu
 * 
 */

public class Contact {
	
	private final String errorMessage1 ="Mandatory Field";
	
	private String firstName;

	
	private String lastName;

	
	private String email;

	
	private String userName;
	
	
	private String password;
	
	
	private String confirmPassword;
	
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public String getConfirmPassword(){
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "Name: " + firstName + " " + lastName + ", Email: " + email;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public boolean getIsValid(){
		return password.equals(confirmPassword);
	}
}
