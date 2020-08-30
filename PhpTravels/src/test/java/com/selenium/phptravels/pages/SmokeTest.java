package com.selenium.phptravels.pages;

import org.testng.annotations.Test;

import com.selenium.library.Base;
import com.selenium.pages.AdminDashBoard;
import com.selenium.pages.AdminPage;
import com.selenium.pages.HomePage;
import com.selenium.pages.UserPage;
import com.selenium.pages.UserPrivatePage;

public class SmokeTest extends Base{
	UserPage user;
	HomePage home;
	AdminPage admin ;
	@Test(priority=1)
	public void phptravels() {
		home = new HomePage();
		home.goto_HomePage();
		home.selectUser();
	    user = new UserPage();
		user.check();
		
	}
	
	@Test(dataProvider = "testdata", dataProviderClass = UserPage.class,priority=2)
	public void userCheck(String username,String password) {
		user.userlogin(username,password);
		UserPrivatePage pr = new UserPrivatePage();
		pr.demoPage();
	}
	@Test(priority=3)
	public void phptravel() {
		home = new HomePage();
		home.goto_HomePage();
		home.selectAdmin();	
	}
	@Test(dataProvider = "admintest", dataProviderClass = AdminPage.class,priority=4)
	public void adminCheck(String username,String password) {
		admin = new AdminPage();
		admin.goto_BackEnd(username, password);
		AdminDashBoard ad = new AdminDashBoard();
		ad.dashBoard();
	}
	}
	
	
	
	


