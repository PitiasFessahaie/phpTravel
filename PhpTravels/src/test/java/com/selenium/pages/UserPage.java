package com.selenium.pages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.selenium.library.Base;
import com.selenium.library.ReadData;

public class UserPage extends Base {
	final static Logger logger = Logger.getLogger(UserPage.class);

	public UserPage check() {
		try {
			String pageTitle = driver.getTitle();
			logger.info("Title is :" + pageTitle);
			assertEquals(pageTitle, "Login");
			lib.customwait(1);

		} catch (Exception e) {
			logger.error("Error :", e);
			assertTrue(false);
		}

		return this;
	}

	@Test(dataProvider = "testdata")
	public void userlogin(String username, String password) {

		try {
			lib.webpage("https://www.phptravels.net/login");
			lib.waitForElementVisibility(By.tagName("h3"));

			//// 4 X testing text in the User-name and Password!!! (( ExpectedFailure = 3 ))
			lib.enterText(By.xpath("//input[@name=\"username\"]"), username);
			lib.enterText(By.xpath("//input[@name=\"password\"]"), password);

			lib.button(By.xpath("//*[@id=\"loginfrm\"]/button"), 1);
			lib.waitForElementToBeClickable(By.xpath("//*[@id=\"bookings\"]/div[2]/div[4]/a"));

			String title = driver.getTitle();
			logger.info("Title :" + title);
			assertEquals(title, "My Account");
		} catch (Exception e) {
			logger.error("Error :Incorrect Credentials(check Username,Password)", e);
			driver.close();
			assertTrue(false);
		}

	}

	@DataProvider(name = "testdata")
	private String[][] UserFeed() { /// User Credentials return to the user testing method???
		ReadData page = new ReadData("src/test/resources/login.xlsx");
		int row = page.getRowCount(0);

		String[][] credentials = new String[row][2];

		for (int i = 0; i < row; i++) {
			credentials[i][0] = page.getData(0, i, 0);
			credentials[i][1] = page.getData(0, i, 1);
		}
		return credentials;

	}

}
