package com.DD.Testcase;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;

public class StandAlone {

	public static void main(String[] args) throws InterruptedException, AWTException, IOException {

		// Initialize WebDriver and WebDriverWait
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		// Maximize window and navigate to the website
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(" https://demo.dealsdray.com/");

		// Fill in username and password
		WebElement username = driver.findElement(By.xpath("//input[@name='username']"));
		username.sendKeys("prexo.mis@dealsdray.com");

		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		password.sendKeys("prexo.mis@dealsdray.com");

		// Click on the login button
		WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
		loginButton.click();

		// Wait for the next page to load
		driver.findElement(By.xpath("//span[contains(text(),'chevron_right')]")).click();

		// Navigate to the Orders section and add bulk orders
		WebElement ordersTab = driver.findElement(By.xpath("//span[normalize-space()='Orders']"));
		ordersTab.click();

		WebElement addBulkOrdersButton = driver.findElement(By.xpath("//button[normalize-space()='Add Bulk Orders']"));
		addBulkOrdersButton.click();

		// Click on the upload button
		WebElement upload = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div/div[2]/div[3]/div/div"));
		upload.click();

		Thread.sleep(2000);

		// Perform file upload using Robot class
		Robot robot = new Robot();
		StringSelection stringSelection = new StringSelection("\"C:\\Users\\Sagar yadrami\\OneDrive\\Desktop\\demo-data.xlsx\"");
		// Copy the file path to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		// Press Enter to confirm the file upload
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(2000);

		// Click on the upload confirmation button
		WebElement uploadConfirmButton = driver.findElement(By.cssSelector("div[class='MuiBox-root css-1xi4464'] button[type='button']"));
		uploadConfirmButton.click();

		WebElement validate = driver.findElement(By.xpath("//*[@id='root']/div/div/div[2]/div/div/div[2]/div[3]/button"));
		validate.click();

		// Handle the alert
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();

		// scrolling to take the screenshot
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[@id='root']/div/div/div[2]/div/div/div[2]/div[4]/table/tbody/tr[7]/td[2]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);

		Thread.sleep(2000);

		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("C:\\Users\\Sagar yadrami\\DealDray2.png"));

		// Quit the WebDriver
		driver.quit();
	}

}
