package com.selenium.library;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.io.Files;

import io.github.bonigarcia.wdm.WebDriverManager;

//********  
// @Title : Library Star
// @author : Pitias T. Fessahaie
// @version : 1.7
// @Updated : 4/1/2020
///********
public class LibraryStar {
	final static Logger logger = Logger.getLogger(LibraryStar.class);

	private static Random r;
	private WebDriver driver;
	public boolean isRemote;
	public SortableElement sortable;
	public List<String> errorScreenshots;
	private boolean isDemoMode = false;

	public LibraryStar(WebDriver driver) { // Creating a Constructor
		this.driver = driver;
	}

	public class SortableElement {

		private static final int OFFSET = 5;

		@FindBy(css = ".ui-sortable-handle")
		private List<WebElement> sortableHandles;

		private Map<String, WebElement> map;
		private Actions action;

		public SortableElement(final WebDriver driver) {
			this.action = new Actions(driver);
			PageFactory.initElements(driver, this);
		}

		public List<String> getItems() {
			return this.sortableHandles.stream().map(WebElement::getText).map(String::trim)
					.collect(Collectors.toList());
		}

		public void reorder(int from, int to) {
			this.reorder(sortableHandles.get(from), sortableHandles.get(to));
		}

		public void reorder(String from, String to) { // move string source to destination
			if (Objects.isNull(map)) {
				map = sortableHandles.stream().collect(Collectors.toMap(ele -> ele.getText(), // key
						ele -> ele // value
				));
			}
			this.reorder(map.get(from), map.get(to));
		}

		private void reorder(WebElement source, WebElement target) {
			this.action.clickAndHold(source).dragAndDropBy(target, OFFSET, OFFSET).build().perform();
		}
	}

	public void draggable(String url, By by) { // Draggable methods
		try {
			driver.get(url);
			Actions act = new Actions(driver);
			driver.switchTo().frame(0);
			for (int i = 1; i < 10; i++) {
				int x = getRandomNumberInRange(50, 120);
				int y = getRandomNumberInRange(50, 120);
				WebElement from = driver.findElement(by);
				act.clickAndHold(from).moveByOffset(x, y).release(from).build().perform();
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	private static int getRandomNumberInRange(int min, int max) {

		try {
			if (min >= max) {
				throw new IllegalArgumentException("max must be greater than min");
			}
			r = new Random();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}

		return r.nextInt((max - min) + 1) + min;
	}

	public void goTo(String url) {
		try {
			driver.get(url);
			this.driver.switchTo().frame(0);
			this.sortable = new SortableElement(driver);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public SortableElement getSortables() {
		return sortable;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	private WebDriver startChrome() {

		try {
			//System.setProperty("webdriver.chrome.driver", "/Users/pitiasfessahaie/Documents/chromedriver");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	private WebDriver startFirefox() {

		try {
			//System.setProperty("webdriver.gecko.driver", "/Users/pitiasfessahaie/Documents/geckodriver");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	public WebDriver startBrowser(String browserType) {
		try {
			if (browserType.toLowerCase().contains("chrome")) {
				driver = startChrome();
			} else if (browserType.toLowerCase().contains("firefox")) {
				driver = startFirefox();
			} else if (browserType.toLowerCase().contains("safari")) {
				driver = startSafari();
			} else if (browserType.toLowerCase().contains("opera")) {
				driver = startOpera();
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	private WebDriver startOpera() {
		try { // System.setProperty("webdriver.opera.driver", "");
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	private WebDriver startSafari() {
		try {
			
			driver = new SafariDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	public void Checkbox(By by, boolean isCheck) { // Check-box and Radio-box
		try {
			if (isCheck == true) {
				WebElement Checkbox = driver.findElement(by);
				if (Checkbox.isSelected()) {
				} else {
					Checkbox.click();
				}
			}

			if (isCheck == false) {
				WebElement Checkbox = driver.findElement(by);
				if (Checkbox.isSelected()) {
					Checkbox.click();
				} else {
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void webpage(String link, int f) {
		try {
			driver.get(link);
			driver.switchTo().frame(f);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void webpage(String link) {
		try {
			driver.get(link);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void safeClickJava(WebElement element) throws Exception {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out.println("Clicking on element with using java script click");

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} else {
				System.out.println("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			logger.error("Element is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.error("Element was not found in DOM " + e.getStackTrace());
		} catch (Exception e) {
			logger.error("Unable to click on element " + e.getStackTrace());
		}
	}

	public void button(By by, int n) throws Exception {
		try {
			for (int i = 0; i < n; i++) {
				WebElement radio = driver.findElement(by);
				highlightElement(radio);
				radio.click();
				Thread.sleep(900);
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void clickdropdown(By by, By by2) throws Exception {
		try {
			List<WebElement> list = driver.findElements(by);
			highlightElement(list.get(0));
			list.get(0).click();
			Thread.sleep(2000);
			driver.findElement(by2).click();
			Thread.sleep(5000);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void dropdown(By by, int Index) {
		try {
			WebElement data = driver.findElement(by); // @@@@
			highlightElement(data);
			data.click();
			Select dropdown = new Select(data);
			dropdown.selectByIndex(Index);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void dropdown(By by, String optionValue) {
		try {
			WebElement data = driver.findElement(by);
			Select dropdown = new Select(data);
			dropdown.selectByVisibleText(optionValue);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void dropdown(String attributevalue, By by) {
		try {
			WebElement data = driver.findElement(by);
			Select dropdown = new Select(data);
			dropdown.selectByValue(attributevalue);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void resize(WebElement elementToResize, int xOffset, int yOffset) {
		try {
			if (elementToResize.isDisplayed()) {
				Actions action = new Actions(driver);
				action.clickAndHold(elementToResize).moveByOffset(xOffset, yOffset).release().build().perform();
				Thread.sleep(300);
			} else {
				logger.info("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			logger.error(
					"Element with " + elementToResize + "is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			logger.error("Element " + elementToResize + " was not found in DOM " + e.getStackTrace());
		} catch (Exception e) {
			logger.error("Unable to resize" + elementToResize + " - " + e.getStackTrace());
		}

	}

	public void highlightElement(WebElement element) {
		try {
			if (isDemoMode == true) {
				for (int i = 0; i < 2; i++) {
					WrapsDriver wrappedElement = (WrapsDriver) element;
					JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow");
					customWait(0.5);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
					customWait(0.5);
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void customWait(double inSecs) {
		try {
			Thread.sleep((long) (inSecs * 1000));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void scrollToElement(WebElement element) {
		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void scrollByoffset(String verticalPixel) {
		try {

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(0," + verticalPixel + ")");
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void scrollByHorizontal(String horizontalPixel) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(horizontalPixel,0)");
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void sendkeyBoard(CharSequence... keysToSend) { // commands like keys.command,keys.up...etc

		try {
			WebElement websiteElement = driver.findElement(By.tagName("body"));
			websiteElement.sendKeys(keysToSend);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public String getCurrentTime() {
		String finalTime = null;
		try {
			Date date = new Date();
			String tempTime = new Timestamp(date.getTime()).toString();
			System.out.println("time: " + tempTime);
			finalTime = tempTime.replace("-", "").replace(" ", "").replace(":", "").replace(".", "");
			System.out.println(finalTime);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return finalTime;

	}

	public void enterText(By by, String texts) {
		try {
			WebElement text = driver.findElement(by);
			highlightElement(text);
			text.clear();
			text.sendKeys(texts);
			customwait(900);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void moveMouseElement(WebElement targetEle) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(targetEle).build().perform();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void moveMouseClick(WebElement targetEle) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(targetEle).click().build().perform();
			customwait(1000);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void moveMouseToElementAndClick(WebElement firstElem, WebElement secEle) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(firstElem).build().perform();
			customwait(1000);
			action.moveToElement(secEle).click().build().perform();
			customwait(1000);
			// driver.navigate().back();

		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void customwait(double sec) {
		try {
			Thread.sleep((long) sec);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public String screenCapture(String screenshotfilename) {
		String filePath = null;
		String fileName = null;
		try {

			fileName = screenshotfilename + getCurrentTime() + ".png";
			filePath = "target/Screenshot/";
			File tempfile = new File(filePath);
			if (!tempfile.exists()) {
				tempfile.mkdirs();
			}
			filePath = tempfile.getAbsolutePath();
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			Files.copy(scrFile, new File(filePath + "/" + fileName));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return filePath + "/" + fileName;
	}

	public WebElement fluentWait(final By by) {
		WebElement targetElem = null;
		try {
			@SuppressWarnings("deprecation")
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
					.pollingEvery(3, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
			targetElem = wait.until(new Function<WebDriver, WebElement>() {
				public WebElement apply(WebDriver driver) {
					return driver.findElement(by);
				}
			});
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return targetElem;
	}

	public WebElement waitForElementPresence(By by) {
		WebElement elem = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			elem = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return elem;
	}

	public WebElement waitForElementVisibility(By by) {
		WebElement elem = null;
		try {
			elem = (new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return elem;
	}

	public WebElement waitForElementToBeClickable(By by) {
		WebElement elem = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			elem = wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return elem;
	}

	public void tab(int x) {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		if (x == 1) {
			driver.switchTo().window(tabs2.get(x));
		} else if (x == 0) {
			driver.close();
			driver.switchTo().window(tabs2.get(x));
		}
	}

	public void setDemoMode(boolean isDemoMode) {
		this.isDemoMode = isDemoMode;

	}

	public WebDriver switchToWindow(int browserIndex) {
		try {
			Set<String> allBrowsers = driver.getWindowHandles();
			Iterator<String> iterator = allBrowsers.iterator();
			List<String> windowHandles = new ArrayList<>();
			while (iterator.hasNext()) {
				String window = iterator.next();
				windowHandles.add(window);
			}
			// switch to index N
			driver.switchTo().window(windowHandles.get(browserIndex));
			highlightElement(By.tagName("body"));
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return driver;
	}

	public void highlightElement(By by) {
		try {
			if (isDemoMode == true) {
				WebElement element = driver.findElement(by);
				for (int i = 0; i < 4; i++) {
					WrapsDriver wrappedElement = (WrapsDriver) element;
					JavascriptExecutor js = (JavascriptExecutor) wrappedElement.getWrappedDriver();
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
							"color: red; border: 2px solid yellow");
					customWait(0.5);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
					customWait(0.5);
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void fileUpload(By by, String fileRelativePath) {
		try {
			WebElement fileUploadElem = driver.findElement(by);
			highlightElement(fileUploadElem);
			File tempFile = new File(fileRelativePath);
			String fullPath = tempFile.getAbsolutePath();
			logger.info("file uploading : " + fullPath);
			fileUploadElem.sendKeys(fullPath);
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public Alert isAlertPresent() {
		Alert alert = null;
		try {
			alert = driver.switchTo().alert();
			logger.info("Popup Alert detected: {" + alert.getText() + "}");
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
		return alert;
	}

	public void closeAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			logger.info("Popup Alert detected: {" + alert.getText() + "}");
			alert.dismiss();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}

	public void acceptAlert() {
		try {
			Alert alert = driver.switchTo().alert();
			logger.info("Popup Alert detected: {" + alert.getText() + "}");
			alert.accept();
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}
	public List<String> automaticallyAttachErrorImgToEmail() {
		List<String> fileNames = new ArrayList<>();
		PropertyManager propertyReader = new PropertyManager("src/test/resources/dynamicConfig.properties");
		String tempTimeStamp = propertyReader.readProperty("sessionTime");
		String numberTimeStamp = tempTimeStamp.replaceAll("_", "");
		long testStartTime = Long.parseLong(numberTimeStamp);		
		File file = new File("target/screenshots"); // first check if error-screenshot folder has file
		if (file.isDirectory()) {
			if (file.list().length > 0) {
				File[] screenshotFiles = file.listFiles();
				for (int i = 0; i < screenshotFiles.length; i++) {
					// checking if file is a file, not a folder
					if (screenshotFiles[i].isFile()) {
						String eachFileName = screenshotFiles[i].getName();
						logger.debug("Testing file names: " + eachFileName);
						int indexOf20 = searchSubstringInString("20", eachFileName);
						String timeStampFromScreenshotFile = eachFileName.substring(indexOf20,
								eachFileName.length() - 4);
						logger.debug("Testing file timestamp: " + timeStampFromScreenshotFile);
						String fileNumberStamp = timeStampFromScreenshotFile.replaceAll("_", "");
						long screenshotfileTime = Long.parseLong(fileNumberStamp);

						testStartTime = Long.parseLong(numberTimeStamp.substring(0, 14));
						screenshotfileTime = Long.parseLong(fileNumberStamp.substring(0, 14));
						if (screenshotfileTime > testStartTime) {
							fileNames.add("target/screenshots/" + eachFileName);
							logger.info("Screenshots attaching: " + eachFileName);
						}}}}}
		errorScreenshots = fileNames;
		return fileNames;
	}
	
	public int searchSubstringInString(String target, String message) {
		int targetIndex = 0;
		for (int i = -1; (i = message.indexOf(target, i + 1)) != -1;) {
			targetIndex = i;
			break;
		}
		return targetIndex;
	}
	public WebDriver startRemoteBrowser(String hubURL, String browser) {		
		DesiredCapabilities cap = null;
		try {
			if (browser.toLowerCase().contains("chrome")) {
				cap = DesiredCapabilities.chrome();
			} else if (browser.toLowerCase().contains("ie")) {
				cap = DesiredCapabilities.internetExplorer();				
				cap.setCapability("ignoreProtectedModeSettings", true);
				cap.setCapability("ie.ensureCleanSession", true);
			} else if (browser.toLowerCase().contains("firefox")) {
				cap = DesiredCapabilities.firefox();
			} else {
				logger.info("You choose: '" + browser.toLowerCase() + "', Currently, framework does't support it.");
				logger.info("starting default browser 'Internet Explorer'");
				cap = DesiredCapabilities.internetExplorer();							
				cap.setCapability("ignoreProtectedModeSettings", true);
				cap.setCapability("ie.ensureCleanSession", true);
			}
			driver = new RemoteWebDriver(new URL(hubURL), cap);
			isRemote = true;
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}		
		return driver;
	}
	public void scrollByVertically(String verticalPixel) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(0," + verticalPixel + ")");
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}
	public void scrollByHorizontally(String horizontalPixel) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("scroll(" + horizontalPixel + ",0)");
		} catch (Exception e) {
			logger.error("Error: ", e);
			assertTrue(false);
		}
	}
}
