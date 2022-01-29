package com.testNg;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.utility.Orep;
import com.utility.library_BusinessFunctions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HeadLessBrowserEdgeBrowser extends library_BusinessFunctions{
	//HtmlUnitDriver driver;
	public static WebDriver driver ;
	
	@Test(priority = 0)
	public void testingHeadlessUsingEdgeBrowser() throws IOException{
		ReadPropertyFile();
		EdgeOptions options = new EdgeOptions();
		//options.addArguments("headless");
		WebDriverManager.edgedriver().setup();
		driver= new EdgeDriver(options);
		driver.get(objProperties.getProperty("GMOonlineURL"));
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		String title = driver.getTitle();
		System.out.println("title:"+title);
		Assert.assertEquals(title, objProperties.getProperty("GMO_onlineTitle"));
		//library_BusinessFunctions.FindElementUnitDriver(driver,Orep.GmoOnlineSumbmitButton).click();
		//library_BusinessFunctions.screenShot(driver);
	}
	
	@Test(priority = 1, dependsOnMethods = { "testingHeadlessUsingEdgeBrowser" })
	public void ValidateEnterGMoOnline() {
		System.out.println("inside ValidateEnterGMoOnline");
		//extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		library_BusinessFunctions.FindElementUnitDriver(driver,Orep.GmoOnlineSumbmitButton).click();
		//waitForPageToLoad();
		//String text = driver.findElement(By.xpath(Orep.TextGmoOnline)).getText();
		String text = library_BusinessFunctions.FindElementUnitDriver(driver,Orep.TextGmoOnline).getText();
		System.out.println(text);
		Assert.assertEquals(text, objProperties.getProperty("GmoOnlineListPageTitle"));
	}

	@Test(priority = 2, dependsOnMethods = { "ValidateEnterGMoOnline" })
	public void ValidateOrderGlacierSunGlasses() {
		System.out.println("inside ValidateOrderGlacierSunGlasses");
		//extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		//driver.findElement(By.xpath(Orep.QTY_BACKPACKS)).sendKeys(objProperties.getProperty("QTY_BACKPACKS"));
		//driver.findElement(By.name(Orep.GmoOnlineSumbmitButton)).click();
		library_BusinessFunctions.FindElementUnitDriver(driver,Orep.QTY_BACKPACKS).sendKeys(objProperties.getProperty("QTY_BACKPACKS"));
		library_BusinessFunctions.FindElementUnitDriver(driver,Orep.GmoOnlineSumbmitButton).click();
		String Title = driver.getTitle();
		Assert.assertEquals(Title, objProperties.getProperty("GMOonlinePlaceOrderTitle"));
		String UnitPrice = library_BusinessFunctions.FindElementUnitDriver(driver,Orep.UnitPrice).getText();
		System.out.println("UnitPrice: " + UnitPrice);
		String floatUnitPrice = UnitPrice.substring(2).trim();
		System.out.println("floatUnitPrice: " + floatUnitPrice);
		float UnitPrice_FloatValue = Float.parseFloat(floatUnitPrice);
		float UnitPrice_FloatCalculatedValue = UnitPrice_FloatValue * 5;
		System.out.println("UnitPrice_FloatCalculatedValue:" + UnitPrice_FloatCalculatedValue);
		String TotalPrice = driver
				.findElement(By.xpath("//table[@cellpadding='4' and @cellspacing='1']/tbody/tr[2]/td[5]")).getText();
		float TotalPricefromWebTable = Float.parseFloat(TotalPrice.substring(2).trim());
		System.out.println("TotalPricefromWebTable:" + TotalPricefromWebTable);
		Assert.assertEquals(UnitPrice_FloatCalculatedValue, TotalPricefromWebTable);
	}

	
}
