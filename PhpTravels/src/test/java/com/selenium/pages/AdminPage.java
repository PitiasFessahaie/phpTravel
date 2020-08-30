package com.selenium.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.selenium.library.Base;
import com.selenium.library.ReadData;

public class AdminPage extends Base{
	
	final static Logger logger = Logger.getLogger(AdminPage.class);
	@Test(dataProvider = "admintest",priority = 3)          
	public AdminPage goto_BackEnd(String username,String password) {   
      try {
    	  //Loading main page
    	  lib.webpage("https://www.phptravels.net/admin");
    	  //Log-In credentials testing 4X   (( ExpectedFailure = 3 ))
    	  lib.enterText(By.xpath("//*[@name=\"email\" and @placeholder =\" \"]"), username );
  		  lib.enterText(By.xpath("//input[@type = \"password\"]"), password);
  		  lib.button(By.xpath("//button[@type = \"submit\"]"), 1);
  		  
  		  lib.waitForElementToBeClickable(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div/div/div[1]/button/div")); 
  		  
  		  //Check point
  		  logger.info("Admin Page Title  :"+driver.getTitle());	
  		  String atitle = driver.getTitle();
  		  assertEquals(atitle, "Dashboard");
		 
		} catch (Exception e) {
			logger.error("Error :Incorrect Credentials(check Username,Password)", e);
			driver.close();
			assertTrue(false);
		} return this;
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
		
		
//		logger.info("Excel data______");
//		logger.info(credentials);
		
		return credentials;

	}
}
