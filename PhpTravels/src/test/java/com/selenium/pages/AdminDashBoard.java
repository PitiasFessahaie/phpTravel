package com.selenium.pages;

import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;


import com.selenium.library.Base;

public class AdminDashBoard extends Base {
	final static Logger logger = Logger.getLogger(AdminDashBoard.class);
	public AdminDashBoard dashBoard() {
		  try {
		 
			  
		  lib.button(By.xpath("//*[@id=\"content\"]/div[2]/div[2]/div/div/div[6]/form/button/div"), 1);
  		  lib.waitForElementVisibility(By.xpath("//button[@class=\"btn btn-danger pull-right\"]"));
  		  //Logging out
  		  lib.button(By.xpath("//*[@id=\"logout\"]/a/strong"), 1);
  		  lib.customwait(3);
		
		  } catch (Exception e) {
				logger.error("Error :", e);
				driver.close();
				assertTrue(false);
			}
		return this;
	}
}
