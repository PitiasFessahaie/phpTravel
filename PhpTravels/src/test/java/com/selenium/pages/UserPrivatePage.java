package com.selenium.pages;

import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;


import com.selenium.library.Base;

public class UserPrivatePage extends Base {
	final static Logger logger = Logger.getLogger(UserPrivatePage.class);
	public UserPrivatePage demoPage() {
 try {
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
		assertTrue(false);}
		
		
		
		return this;
	}
}
