package wm.edu.cs420;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import wm.edu.cs420.Data.Account;
import wm.edu.cs420.Data.HibernateDAO;

@Transactional
@ContextConfiguration(locations ={  "file:src/main/webapp/WEB-INF/spring/miicConfig.xml"})
public class UserDAOTest extends AbstractJUnit4SpringContextTests {
	
	private HibernateDAO dao = new HibernateDAO();
	/**
	@Test
	public void storeAccountTest(){
		Account a = new Account();
		a.setFirstName("tim");
		a.setLastName("cohen");
		a.setPassword("password");
		a.setUserName("tacohen");
		dao.setAccount(a);
		Account b = new Account();
		b = dao.getAccount("tacohen");
		assertTrue(b.getFirstName().equals("tim"));
	}
*/
}
