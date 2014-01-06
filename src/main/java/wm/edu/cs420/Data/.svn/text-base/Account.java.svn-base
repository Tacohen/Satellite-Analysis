package wm.edu.cs420.Data;



import gov.nasa.miic.common.ICPlan;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity(name="USERS")
public class Account {
	
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	// the varible to hold the information for an account
	@Column(name = "FIRST_NAME", unique = false, nullable = false)
    private String firstName;
	
	
	@Column(name = "LAST_NAME", unique = false, nullable = false)
    private String lastName;
    
	@Column(name="USERNAME", unique = true, nullable = false)
	private String userName;
	
	@Column(name="email")
	private String email;
	
	
	@Column(name="PASSWORD", unique=false, nullable = false)
	private String password;
	
	@Column(name="ENABLED")
	private boolean enabled;
	
	@OneToMany(targetEntity = ICPlan.class, fetch = FetchType.EAGER)
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
	@Fetch(FetchMode.SELECT)
	@OrderBy("name")
	private List<ICPlan> plans;
	
	@OneToMany(targetEntity = UserRole.class)
	@JoinColumn(name="USER_ID")
	private List<UserRole> userRoles;
   
    // default constructor
    public Account() {
    	userRoles= new ArrayList<UserRole>();
    }


    public Account(Contact contact){
    	this.firstName =contact.getFirstName();
    	this.lastName = contact.getLastName();
    	this.password = contact.getPassword();
    	this.userName = contact.getUserName();
    	this.email = contact.getEmail();
    	this.enabled = true;
    	this.userRoles= new ArrayList<UserRole>();
    }


	public String getPassword() {
		return password;
	}





	public void setPassword(String password) {
		
		this.password = password;
	}





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





	public String getUserName() {
		return userName;
	}





	public void setUserName(String userName) {
		this.userName = userName;
	}





	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public boolean getEnabled() {
		return enabled;
	}





	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}


	public List<UserRole> getUserRoles() {
		return userRoles;
	}


	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
    
    public void addUserRole(UserRole userRole){
    	userRoles.add(userRole);
    }


	public List<ICPlan> getPlans() {
		return plans;
	}


	public void setPlans(List<ICPlan> plans) {
		this.plans = plans;
	}
    
	public void addPlan(ICPlan plan){
		plans.add(plan);
	}
	
	public ICPlan getPlan(int id){
		for(ICPlan plan: plans){
			if(plan.getId() == id){
				return plan;
			}
		}
		return null;
	}
}