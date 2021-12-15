package com.testNg;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

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

public class testNg2 {
	WebDriver driver;
	
	@Test(priority=0)
	public void ValidateGMOonlineLoadedSuccessfully() {
		System.out.println("inside ValidateLaunchBrowser");
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Welcome to Green Mountain Outpost");
		
	}
	
	@Test(priority=1,dependsOnMethods={"ValidateGMOonlineLoadedSuccessfully"})
	public void ValidateEnterGMoOnline(){
		System.out.println("inside ValidateEnterGMoOnline");
		driver.findElement(By.name("bSubmit")).click();
		String text = driver.findElement(By.xpath("//h1[contains(text(),'OnLine Catalog')]")).getText();
		System.out.println(text);
		Assert.assertEquals(text, "OnLine Catalog");
	}
	
	@Test(priority=2, dependsOnMethods={"ValidateEnterGMoOnline"})
	public void ValidateOrderGlacierSunGlasses(){
		System.out.println("inside ValidateOrderGlacierSunGlasses");
		driver.findElement(By.xpath("//input[@name='QTY_BACKPACKS']")).sendKeys("5");
		driver.findElement(By.name("bSubmit")).click();
		String Title=driver.getTitle();
		Assert.assertEquals(Title, "Place Order");
		String UnitPrice=driver.findElement(By.xpath("//table[@cellpadding='4' and @cellspacing='1']/tbody/tr[2]/td[4]")).getText();
		System.out.println("UnitPrice: "+UnitPrice);
		String floatUnitPrice=UnitPrice.substring(2).trim();
		System.out.println("floatUnitPrice: "+floatUnitPrice);
		float UnitPrice_FloatValue = Float.parseFloat(floatUnitPrice);
		float UnitPrice_FloatCalculatedValue = UnitPrice_FloatValue*5;
		System.out.println("UnitPrice_FloatCalculatedValue:"+UnitPrice_FloatCalculatedValue);
		String TotalPrice = driver.findElement(By.xpath("//table[@cellpadding='4' and @cellspacing='1']/tbody/tr[2]/td[5]")).getText();
		float TotalPricefromWebTable = Float.parseFloat(TotalPrice.substring(2).trim());
		System.out.println("TotalPricefromWebTable:"+TotalPricefromWebTable);
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
	}

	@AfterTest
	public void afterTest() {
		System.out.println("inside afterTest");
	}

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("inside beforeSuite");
		/*WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();*/
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		driver.get("http://demo.borland.com/gmopost/");
		driver.manage().window().maximize();
		//implicit wait : gloabal waiting mechanism applicable for all web elements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("inside afterSuite");
	}

}
