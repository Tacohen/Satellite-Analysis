package wm.edu.cs420.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name="user_roles")
public class UserRole {

	public static final String ROLE_USER = "ROLE_USER";
	
	@Id
	@Column(name="user_role_id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userRoleId;
	
	@ManyToOne(targetEntity=Account.class)
	@JoinColumn(name="USER_ID", referencedColumnName ="USER_ID", nullable=false)
	private Account user;
	
	@Column(name="authority", nullable = false)
	private String authority;

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
	
}
