package com.selenium.phptravels.pages;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.library.Base;
import com.selenium.library.ReadData;



public class Home extends Base { /// No Scenario required ?//
	final static Logger logger = Logger.getLogger(Base.class);

	@Test(priority=1)
	public void goto_HomePage() {               // Testing the Web-site
		try {
			                                      
			lib.webpage("https://phptravels.com/demo/");
			String pageTitle = driver.getTitle(); 
			logger.info(pageTitle);
			AssertJUnit.assertEquals(pageTitle, "Demo Script Test drive - PHPTRAVELS");
			lib.customwait(1);
			// Testing the Price button
			lib.button(By.xpath("//*[@id=\"main-menu\"]/ul/li[2]/a"), 1);
			lib.customwait(2);
			String Pricing = driver.getTitle();
			logger.info("The tile is : " + Pricing);
			AssertJUnit.assertEquals(Pricing, "Order purchase script onetime payment - PHPTRAVELS");
			driver.navigate().back();
			lib.customwait(2);
			// Testing the User Pages
			lib.waitForElementVisibility(By.xpath("//a[@class=\"btn btn-primary btn-lg btn-block\"]"));
			lib.button(By.xpath("//a[@class=\"btn btn-primary btn-lg btn-block\"]"), 1);
			lib.customwait(2);
			// Changing tab
			lib.tab(1);
			// Selecting the Login Screen
			lib.waitForElementVisibility(By.xpath("/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/a"));
			lib.clickdropdown(By.xpath("/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/a"),
					By.xpath("//a[@class=\"dropdown-item active tr\"]"));
			lib.customwait(2);
			
			
			
			
			

		} catch (Exception e) {
			logger.error("Error :", e);
			AssertJUnit.assertTrue(false);
		}

	}
	 
	                                                                 /// Front-End Automation
	@Test(dataProvider = "testdata",priority = 2)        
	public void goto_FrontEnd(String username,String password) {
		try {
		lib.webpage("https://www.phptravels.net/login");
                                       
		lib.waitForElementVisibility(By.tagName("h3"));
		                
		 //// 4 X testing text in the User-name and Password!!!      (( ExpectedFailure = 3 ))
		lib.enterText(By.xpath("//input[@name=\"username\"]"), username );
		lib.enterText(By.xpath("//input[@name=\"password\"]"), password);
		
		lib.button(By.xpath("//*[@id=\"loginfrm\"]/button"), 1);
	    lib.waitForElementToBeClickable(By.xpath("//*[@id=\"bookings\"]/div[2]/div[4]/a"));
		
	    String title = driver.getTitle();
		logger.info("Title :"+title);
		AssertJUnit.assertEquals(title, "My Account");
		
		
		lib.button(By.xpath("//*[@id=\"bookings\"]/div[2]/div[4]/a"), 1);
		 lib.waitForElementToBeClickable(By.xpath("//*[@id=\"bookings\"]/div[2]/div[4]/a"));
		logger.info("Personal Page Title  :"+driver.getTitle());
		
		lib.tab(1);
        lib.clickdropdown(By.xpath("//*[@id=\"dropdownCurrency\"]"), By.xpath("//*[@id=\"header-waypoint-sticky\"]/div[1]/div/div/div[2]/div/ul/li[2]/div/div/div/a[2]"));
        lib.waitForElementVisibility(By.tagName("h3"));
        
        lib.tab(0);
        lib.customwait(2);
		
		
		} catch (Exception e) {
			logger.error("Error :", e);
			driver.close();
			AssertJUnit.assertTrue(false);
		}
		
		
	}	
	
	@Test(dataProvider = "admintest",priority = 3)          
	public void goto_BackEnd(String username,String password) {   
      try {
    	  //Loading main page
    	  lib.webpage("https://www.phptravels.net/admin");
//    	  lib.waitForElementVisibility(By.tagName("div"));
//    	  lib.button(By.xpath("//*[@id=\"Main\"]/section[2]/div/div/div[3]/div/div/div[2]/div[2]/div/div[1]/div/a"), 1);
//    	  //Changing Tab
//    	  lib.tab(1);
    	  //Log-In credentials testing 4X   (( ExpectedFailure = 3 ))
    	  lib.enterText(By.xpath("//*[@name=\"email\" and @placeholder =\" \"]"), username );
  		  lib.enterText(By.xpath("//input[@type = \"password\"]"), password);
  		  lib.button(By.xpath("//button[@type = \"submit\"]"), 1);
  		  lib.waitForElementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div/div/div[1]/button/div")); 
  		  
  		  //Check point
  		  logger.info("Admin Page Title  :"+driver.getTitle());	
  		  String atitle = driver.getTitle();
  		  AssertJUnit.assertEquals(atitle, "Dashboard");
		 // Checking the DataBase	
  		  lib.button(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div/div/div[6]/form/button/div"), 1);
  		  lib.waitForElementVisibility(By.xpath("//button[@class=\"btn btn-danger pull-right\"]"));
  		  //Logging out
  		  lib.button(By.xpath("//*[@id=\"logout\"]/a/strong"), 1);
  		  lib.customwait(3);
  		  
		} catch (Exception e) {
			logger.error("Error :", e);
			driver.close();
			AssertJUnit.assertTrue(false);
		}
		}
		
		
	
	
	
	@DataProvider(name = "testdata")
	public String[][] UserFeed() 
	{ /// User Credentials return to the user testing method???
		ReadData page = new ReadData("src/test/resources/login.xlsx");
		int row = page.getRowCount(0);

		String[][] credentials = new String[row][2];

		for (int i = 0; i < row; i++) 
		{
			credentials[i][0] = page.getData(0, i, 0);
			credentials[i][1] = page.getData(0, i, 1);
		}
		return credentials;

	}
	@DataProvider(name = "admintest")
	public String[][] AdminFeed() 
	{ /// Admin Credentials needs to return to the admin-testing method???
		ReadData page = new ReadData("src/test/resources/adminlogin.xlsx");
		int row = page.getRowCount(0);

		String[][] credentials = new String[row][2];

		for (int i = 0; i < row; i++) 
		{
			credentials[i][0] = page.getData(0, i, 0);
			credentials[i][1] = page.getData(0, i, 1);
		}
		return credentials;

	}

	
	
}
