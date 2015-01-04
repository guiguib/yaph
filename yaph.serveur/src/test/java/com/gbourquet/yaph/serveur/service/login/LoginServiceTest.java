package com.gbourquet.yaph.serveur.service.login;

import junit.framework.TestCase;

import com.gbourquet.yaph.serveur.service.LoginService;
import com.gbourquet.yaph.serveur.service.exception.ServiceException;
import com.gbourquet.yaph.serveur.util.BeanFactory;


public class LoginServiceTest extends TestCase {
	 
    private LoginService service;
 
    public LoginServiceTest(){
    }
 
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
 
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        service = (LoginService) BeanFactory.getInstance().getService("loginService");
    }
 
    public void testGetUtilisateur(){
 
        try {
        	com.gbourquet.yaph.serveur.metier.generated.Account account = service.login("admin", "admin");
        	//test
            assertNotNull(account);
            assertNotNull(account.getNom());
            }
        catch (ServiceException e)
        {
        	fail();
        }
    }
}
