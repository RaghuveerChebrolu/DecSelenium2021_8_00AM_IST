package com.testNg;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import com.utility.Orep;

import com.utility.library_BusinessFunctions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class testNg4 extends library_BusinessFunctions {
	
	
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
		//driver.findElement(By.name(Orep.GmoOnlineSumbmitButton)).click();
		library_BusinessFunctions.FindElement(Orep.GmoOnlineSumbmitButton).click();
		waitForPageToLoad();
		String text = library_BusinessFunctions.FindElement(Orep.TextGmoOnline).getText();
		System.out.println(text);
		Assert.assertEquals(text, objProperties.getProperty("GmoOnlineListPageTitle"));
	}

	@Test(priority = 2, dependsOnMethods = { "ValidateEnterGMoOnline" })
	public void ValidateOrderGlacierSunGlasses() {
		System.out.println("inside ValidateOrderGlacierSunGlasses");
		driver.findElement(By.xpath(Orep.QTY_BACKPACKS)).sendKeys(objProperties.getProperty("QTY_BACKPACKS"));
		driver.findElement(By.name(Orep.GmoOnlineSumbmitButton)).click();
		String Title = driver.getTitle();
		Assert.assertEquals(Title, objProperties.getProperty("GMOonlinePlaceOrderTitle"));
		String UnitPrice = driver
				.findElement(By.xpath(Orep.UnitPrice)).getText();
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
	@Test(priority = 3)
	public void ValidatingAlerts() throws InterruptedException{
		System.out.println("inside ValidatingAlerts");
		driver.navigate().to(objProperties.getProperty("AlertURL"));
		waitForPageToLoad();
		driver.findElement(By.id("alertButton")).click();
		Alert objAlert1 = driver.switchTo().alert();
		String Alert1Text = objAlert1.getText();
		Assert.assertEquals(Alert1Text, objProperties.getProperty("Alert1Text"));
		objAlert1.accept();
		
		driver.findElement(By.id("timerAlertButton")).click();
		Thread.sleep(5000);
		Alert objAlert2 = driver.switchTo().alert();
		String Alert2Text = objAlert2.getText();
		//Assert.assertEquals(Alert2Text, objProperties.getProperty("Alert2Text"));
		SoftAssert objSoftAssert = new SoftAssert();
		objSoftAssert.assertEquals(Alert2Text, objProperties.getProperty("Alert2Text"));
		objAlert1.accept();
		
		driver.findElement(By.id("confirmButton")).click();
		Alert objAlert3 = driver.switchTo().alert();
		objAlert3.dismiss();
		String Alert3Result = driver.findElement(By.id("confirmResult")).getText();
		objSoftAssert.assertEquals(Alert3Result, objProperties.getProperty("Alert3ResultTextCancel"));
		
		driver.findElement(By.id("promtButton")).click();
		Alert objAlert4 = driver.switchTo().alert();
		objAlert4.sendKeys(objProperties.getProperty("Alert4Textbox"));
		objAlert4.accept();
		String Alert4Result = driver.findElement(By.id("promptResult")).getText();
		Assert.assertEquals(Alert4Result, objProperties.getProperty("Alert4Result"));
		
		driver.navigate().back();
		Thread.sleep(3000);
		driver.navigate().forward();
		objSoftAssert.assertAll();
	}
	
	@Test(priority=4)
	public void HandlingFrames(){
		System.out.println("inside HandlingFrames");
		driver.navigate().to(objProperties.getProperty("FramesURL"));
		waitForPageToLoad();
		driver.switchTo().frame("SingleFrame");
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("inside single frame");
		driver.switchTo().defaultContent();//VVI -> switch bach to normal DOM from frame 
		driver.findElement(By.xpath("//a[@href='#Multiple']")).click();
		
		WebElement MultipleFrameElement = driver.findElement(By.xpath("//iframe[@src='MultipleFrames.html']"));
		driver.switchTo().frame(MultipleFrameElement);
		
		WebElement SingleFrameElement = driver.findElement(By.xpath("//iframe[@src='SingleFrame.html']"));
		driver.switchTo().frame(SingleFrameElement);
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(objProperties.getProperty("framewithInFrame"));
		driver.switchTo().defaultContent();
	}
	
	@Test(priority=5)
	public void VaidateHandlingWindows(){
		System.out.println("inside VaidateHandlingWindows");
		driver.navigate().to(objProperties.getProperty("nxtgenaiacademyURL"));
		waitForPageToLoad();
		String ParentWindow = driver.getWindowHandle();
		library_BusinessFunctions.FindElement(Orep.NewBrowserWindow).click();
		Set<String> Allwindows=driver.getWindowHandles();
		System.out.println("windpws count:"+Allwindows);
		for (String IndividualWindow : Allwindows){
			driver.switchTo().window(IndividualWindow);
			String title = driver.getTitle();
			System.out.println("title:"+title);
			//title:
			//title:
			if(title.equals("NxtGen A.I Academy � Learn With Clarity")){
				JavascriptExecutor js = (JavascriptExecutor)driver;//downcasting
				js.executeScript("window.scrollBy(0, 1000)");
				Boolean flag = library_BusinessFunctions.FindElement(Orep.newBrowserWindowTestNgFramework).isEnabled();
				System.out.println("flag:"+flag);
				driver.manage().window().maximize();
				// js.executeScript("window.scrollBy(0,1000)");//To scroll vertically
				// Down by 1000 pixels
				// js.executeScript("window.scrollBy(0,-500)");//To scroll vertically Up
				// by 500 pixels
				// js.executeScript("window.scrollBy(500,0)");//To scroll horizontally
				// right by 500 pixels
				// js.executeScript("window.scrollBy(-500,0)");//To scroll horizontally
				// left by 500 pixels

				/*WebElement element = library.FindElement(ObjRepository.DoubleCickFrame);
				js.executeScript("arguments[0].scrollIntoView();", element);*/
			}else if(title.equals("Demo Site � Multiple Windows � NxtGen A.I Academy")){
				String numberofvisits = library_BusinessFunctions.FindElement(Orep.nxtgenaiacademyNumberofVisits).getText();
				System.out.println("numberofvisits:"+numberofvisits);
			}
		}
		
	}
	
	@Test(priority=6)
	public void HandlingMouseOpeartions() throws InterruptedException{
		System.out.println("inside HandlingMouseOpeartions");
		driver.navigate().to(objProperties.getProperty("mouseOpeartionRightClick"));
		waitForPageToLoad();
		//right click
		Actions objAction = new Actions(driver);
		WebElement element = library_BusinessFunctions.FindElement(Orep.MouseOpearationRightClick);
		Thread.sleep(3000);
		objAction.contextClick(element).build().perform();//right click 
		WebElement element1 = library_BusinessFunctions.FindElement(Orep.MouseOpearationRightclickCopy);
		objAction.click(element1).build().perform();
		Thread.sleep(3000);
		Alert objAlert= driver.switchTo().alert();
		String textAlert = objAlert.getText();
		System.out.println("textAlert:"+textAlert);
		Assert.assertEquals(textAlert,objProperties.getProperty("mouseOpeartionRightclickCopyActionText") );
		objAlert.accept();
		library_BusinessFunctions.screenShot();
		
		//doube click
		driver.navigate().to(objProperties.getProperty("mouseOpeartionDoubleClick"));
		waitForPageToLoad();
		WebElement frameElement = library_BusinessFunctions.FindElement(Orep.MouseOpearationDoubleClickFrame);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();", frameElement);
		driver.switchTo().frame(frameElement);
		WebElement DoubleClick = library_BusinessFunctions.FindElement(Orep.MouseOpearationDoubleClickbox);
		objAction.doubleClick(DoubleClick).build().perform();
		
		Color BackGroundColor = Color
				.fromString(library_BusinessFunctions.FindElement(Orep.MouseOpearationDoubleClickbox).getCssValue("background-color"));
		System.out.println("BackGroundColor:" + BackGroundColor);
		String ActualBackGroundColor = BackGroundColor.asRgba();
		System.out.println("ActualBackGroundColor:" + ActualBackGroundColor);
		Assert.assertEquals(ActualBackGroundColor, "rgba(255, 255, 0, 1)");
		driver.switchTo().defaultContent();
		library_BusinessFunctions.screenShot();
		
		//drag and drop
		driver.navigate().to(objProperties.getProperty("mouseOperationDragAndDrop"));
		waitForPageToLoad();
		WebElement frameElement2 = library_BusinessFunctions.FindElement(Orep.MouseOpearationDragAndDropFrame);
		driver.switchTo().frame(frameElement2);
		WebElement drag = library_BusinessFunctions.FindElement(Orep.MouseOpearationdrag);
		WebElement drop = library_BusinessFunctions.FindElement(Orep.MouseOpearationdrop);
		//objAction.dragAndDrop(drag, drop).build().perform();
		objAction.clickAndHold(drag);
		objAction.moveToElement(drop).build().perform();
		library_BusinessFunctions.screenShot();
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
			library_BusinessFunctions.ReadPropertyFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("inside afterSuite");
	}

}
