package com.testNg;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class testNg3 {
	WebDriver driver;
	Properties objProperties = new Properties();

	@Test(priority = 0)
	public void ValidateGMOonlineLoadedSuccessfully() {
		System.out.println("inside ValidateLaunchBrowser");
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title,objProperties.getProperty("GMO_onlineTitle") );

	}

	@Test(priority = 1, dependsOnMethods = { "ValidateGMOonlineLoadedSuccessfully" })
	public void ValidateEnterGMoOnline() {
		System.out.println("inside ValidateEnterGMoOnline");
		driver.findElement(By.name("bSubmit")).click();
		String text = driver.findElement(By.xpath("//h1[contains(text(),'OnLine Catalog')]")).getText();
		System.out.println(text);
		Assert.assertEquals(text, objProperties.getProperty("GmoOnlineListPageTitle"));
	}

	@Test(priority = 2, dependsOnMethods = { "ValidateEnterGMoOnline" })
	public void ValidateOrderGlacierSunGlasses() {
		System.out.println("inside ValidateOrderGlacierSunGlasses");
		driver.findElement(By.xpath("//input[@name='QTY_BACKPACKS']")).sendKeys(objProperties.getProperty("QTY_BACKPACKS"));
		driver.findElement(By.name("bSubmit")).click();
		String Title = driver.getTitle();
		Assert.assertEquals(Title, objProperties.getProperty("GMOonlinePlaceOrderTitle"));
		String UnitPrice = driver
				.findElement(By.xpath("//table[@cellpadding='4' and @cellspacing='1']/tbody/tr[2]/td[4]")).getText();
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

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("inside beforeMethod");
	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("inside afterMethod");
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("inside beforeClass");
	}

	@AfterClass
	public void afterClass() {
		System.out.println("inside afterClass");
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("inside beforeTest");
		LaunchBrowser();
	}

	@AfterTest
	public void afterTest() {
		System.out.println("inside afterTest");
	}

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("inside beforeSuite");
		try {
			ReadPropertyFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ReadPropertyFile() throws IOException {
		System.out.println(System.getProperty("user.dir"));
		File objFile = new File(
				System.getProperty("user.dir") + "//src//test//resources//ConfigurationProperty.properties");
		try {
			FileInputStream ObjFileInputStream = new FileInputStream(objFile);
			objProperties.load(ObjFileInputStream);
			objProperties.getProperty("browser");
			System.out.println("The browser from property file:" + objProperties.getProperty("browser"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void LaunchBrowser() {
		String Browser = objProperties.getProperty("browser");
		switch (Browser) {
		case "IE":
			WebDriverManager.iedriver().setup();
			driver=new InternetExplorerDriver();
			break;
		case "Chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "Edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;	
		default:
			
		}
		driver.get(objProperties.getProperty("GMOonlineURL"));
		driver.manage().window().maximize();
		//implicit wait : gloabal waiting mechanism applicable for all web elements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("inside afterSuite");
	}

}
