package com.selenium.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.selenium.library.Base;

public class HomePage extends Base {
	final static Logger logger = Logger.getLogger(HomePage.class);

	public HomePage goto_HomePage() {
		try {
			lib.webpage("https://phptravels.com/demo/");
			String pageTitle = driver.getTitle();
			logger.info(pageTitle);
			assertEquals(pageTitle, "Demo Script Test drive - PHPTRAVELS");
			lib.customwait(1);

		} catch (Exception e) {
			logger.error("Error :", e);
			assertTrue(false);
		}
		return this;
	}

	public HomePage selectUser() {

		select("frontEnd");
		return this;
	}

	public HomePage selectAdmin() {

		select("backEnd");
		return this;
	}

	private void select(String link) {
		if (link.contains("frontEnd")) {
			try {
				lib.button(By.xpath("//a[@class=\"btn btn-primary btn-lg btn-block\"]"), 1);

				lib.customwait(2);
				// Changing tab
				lib.switchToWindow(1);
				// Selecting the Login Screen
				lib.waitForElementVisibility(
						By.xpath("/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/a"));
				lib.clickdropdown(By.xpath("/html/body/div[2]/header/div[1]/div/div/div[2]/div/ul/li[3]/div/a"),
						By.xpath("//a[@class=\"dropdown-item active tr\"]"));
				lib.customwait(2);

			} catch (Exception e) {
				logger.error("Error :", e);
				assertTrue(false);
			}
		} else if (link.contains("backEnd")) {

			try {
				//lib.waitForElementVisibility(By.tagName("div"));
				lib.button(By.xpath("//*[@id=\"Main\"]/section[2]/div/div/div[3]/div/div/div[2]/div[2]/div/div[1]/div/a"),1);
				// Changing Tab
				lib.switchToWindow(1);
				lib.customwait(1);
			} catch (Exception e) {
				logger.error("Error :", e);
				assertTrue(false);
			}
		}

	}

}
