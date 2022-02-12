package com.testNg;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.utility.Orep;
import com.utility.constants;
import com.utility.library_BusinessFunctions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class testNg4 extends library_BusinessFunctions {
	public static int pageNumber = 1;

	@Test(priority = 0)
	public void ValidateGMOonlineLoadedSuccessfully() {
		System.out.println("inside ValidateLaunchBrowser");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, objProperties.getProperty("GMO_onlineTitle"));

	}

	@Test(priority = 1, dependsOnMethods = { "ValidateGMOonlineLoadedSuccessfully" })
	public void ValidateEnterGMoOnline() {
		System.out.println("inside ValidateEnterGMoOnline");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		// driver.findElement(By.name(Orep.GmoOnlineSumbmitButton)).click();
		library_BusinessFunctions.FindElement(Orep.GmoOnlineSumbmitButton).click();
		waitForPageToLoad();
		String text = library_BusinessFunctions.FindElement(Orep.TextGmoOnline).getText();
		System.out.println(text);
		Assert.assertEquals(text, objProperties.getProperty("GmoOnlineListPageTitle"));
	}

	@Test(priority = 2, dependsOnMethods = { "ValidateEnterGMoOnline" })
	public void ValidateOrderGlacierSunGlasses() {
		System.out.println("inside ValidateOrderGlacierSunGlasses");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		driver.findElement(By.xpath(Orep.QTY_BACKPACKS)).sendKeys(objProperties.getProperty("QTY_BACKPACKS"));
		driver.findElement(By.name(Orep.GmoOnlineSumbmitButton)).click();
		String Title = driver.getTitle();
		Assert.assertEquals(Title, objProperties.getProperty("GMOonlinePlaceOrderTitle"));
		String UnitPrice = driver.findElement(By.xpath(Orep.UnitPrice)).getText();
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
	public void ValidatingAlerts() throws InterruptedException {
		System.out.println("inside ValidatingAlerts");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
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
		// Assert.assertEquals(Alert2Text,
		// objProperties.getProperty("Alert2Text"));
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

	@Test(priority = 4)
	public void HandlingFrames() {
		System.out.println("inside HandlingFrames");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		driver.navigate().to(objProperties.getProperty("FramesURL"));
		waitForPageToLoad();
		driver.switchTo().frame("SingleFrame");
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys("inside single frame");
		driver.switchTo().defaultContent();// VVI -> switch bach to normal DOM
											// from frame
		driver.findElement(By.xpath("//a[@href='#Multiple']")).click();

		WebElement MultipleFrameElement = driver.findElement(By.xpath("//iframe[@src='MultipleFrames.html']"));
		driver.switchTo().frame(MultipleFrameElement);

		WebElement SingleFrameElement = driver.findElement(By.xpath("//iframe[@src='SingleFrame.html']"));
		driver.switchTo().frame(SingleFrameElement);
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(objProperties.getProperty("framewithInFrame"));
		driver.switchTo().defaultContent();
	}

	@Test(priority = 5)
	public void VaidateHandlingWindows() {
		System.out.println("inside VaidateHandlingWindows");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		driver.navigate().to(objProperties.getProperty("nxtgenaiacademyURL"));
		waitForPageToLoad();
		String ParentWindow = driver.getWindowHandle();
		library_BusinessFunctions.FindElement(Orep.NewBrowserWindow).click();
		Set<String> Allwindows = driver.getWindowHandles();
		System.out.println("windpws count:" + Allwindows);
		for (String IndividualWindow : Allwindows) {
			driver.switchTo().window(IndividualWindow);
			String title = driver.getTitle();
			System.out.println("title:" + title);
			// title:
			// title:
			if (title.equals("NxtGen A.I Academy – Learn With Clarity")) {
				// JavascriptExecutor js =
				// (JavascriptExecutor)driver;//downcasting
				// js.executeScript("window.scrollBy(0, 1000)");
				library_BusinessFunctions.ScrollDown(constants.NxtGenAIAcademy);
				Boolean flag = library_BusinessFunctions.FindElement(Orep.newBrowserWindowTestNgFramework).isEnabled();
				System.out.println("flag:" + flag);
				driver.manage().window().maximize();
				// js.executeScript("window.scrollBy(0,1000)");//To scroll
				// vertically
				// Down by 1000 pixels
				// js.executeScript("window.scrollBy(0,-500)");//To scroll
				// vertically Up
				// by 500 pixels
				// js.executeScript("window.scrollBy(500,0)");//To scroll
				// horizontally
				// right by 500 pixels
				// js.executeScript("window.scrollBy(-500,0)");//To scroll
				// horizontally
				// left by 500 pixels

				/*
				 * WebElement element =
				 * library.FindElement(ObjRepository.DoubleCickFrame);
				 * js.executeScript("arguments[0].scrollIntoView();", element);
				 */
			} else if (title.equals("Demo Site – Multiple Windows – NxtGen A.I Academy")) {
				String numberofvisits = library_BusinessFunctions.FindElement(Orep.nxtgenaiacademyNumberofVisits)
						.getText();
				System.out.println("numberofvisits:" + numberofvisits);
			}
		}

	}

	@Test(priority = 6)
	public void HandlingMouseOpeartions() throws InterruptedException {
		System.out.println("inside HandlingMouseOpeartions");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		driver.navigate().to(objProperties.getProperty("mouseOpeartionRightClick"));
		waitForPageToLoad();
		// right click
		Actions objAction = new Actions(driver);
		WebElement element = library_BusinessFunctions.FindElement(Orep.MouseOpearationRightClick);
		Thread.sleep(3000);
		objAction.contextClick(element).build().perform();// right click
		WebElement element1 = library_BusinessFunctions.FindElement(Orep.MouseOpearationRightclickCopy);
		objAction.click(element1).build().perform();
		Thread.sleep(3000);
		Alert objAlert = driver.switchTo().alert();
		String textAlert = objAlert.getText();
		System.out.println("textAlert:" + textAlert);
		Assert.assertEquals(textAlert, objProperties.getProperty("mouseOpeartionRightclickCopyActionText"));
		objAlert.accept();
		library_BusinessFunctions.screenShot();

		// doube click
		driver.navigate().to(objProperties.getProperty("mouseOpeartionDoubleClick"));
		waitForPageToLoad();
		WebElement frameElement = library_BusinessFunctions.FindElement(Orep.MouseOpearationDoubleClickFrame);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", frameElement);
		driver.switchTo().frame(frameElement);
		WebElement DoubleClick = library_BusinessFunctions.FindElement(Orep.MouseOpearationDoubleClickbox);
		objAction.doubleClick(DoubleClick).build().perform();

		Color BackGroundColor = Color.fromString(library_BusinessFunctions
				.FindElement(Orep.MouseOpearationDoubleClickbox).getCssValue("background-color"));
		System.out.println("BackGroundColor:" + BackGroundColor);
		String ActualBackGroundColor = BackGroundColor.asRgba();
		System.out.println("ActualBackGroundColor:" + ActualBackGroundColor);
		Assert.assertEquals(ActualBackGroundColor, "rgba(255, 255, 0, 1)");
		driver.switchTo().defaultContent();
		library_BusinessFunctions.screenShot();

		// drag and drop
		driver.navigate().to(objProperties.getProperty("mouseOperationDragAndDrop"));
		waitForPageToLoad();
		WebElement frameElement2 = library_BusinessFunctions.FindElement(Orep.MouseOpearationDragAndDropFrame);
		driver.switchTo().frame(frameElement2);
		WebElement drag = library_BusinessFunctions.FindElement(Orep.MouseOpearationdrag);
		WebElement drop = library_BusinessFunctions.FindElement(Orep.MouseOpearationdrop);
		// objAction.dragAndDrop(drag, drop).build().perform();
		objAction.clickAndHold(drag);
		objAction.moveToElement(drop).build().perform();
		library_BusinessFunctions.screenShot();
	}

	@Test(priority = 7)
	public void HandlingWebTable() {
		System.out.println("inside HandlingWebTable");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		driver.navigate().to(objProperties.getProperty("WebTableURL"));
		waitForPageToLoad();
		ScrollDown(constants.Webtablescrolldown);
		List<WebElement> AllLastNames = library_BusinessFunctions.FindElements(Orep.WebTableLastNames);
		int rows = AllLastNames.size();
		for (int page = 1; page <= 6; page++) {
			for (int i = 1; i <= rows; i++) {
				String LastName = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[3]")).getText();
				if (LastName.equals(constants.WebTanbleLastName)) {
					String FirstName = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[2]"))
							.getText();
					String Position = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[4]"))
							.getText();
					String Office = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[5]"))
							.getText();
					String StartDate = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[6]"))
							.getText();
					String Salary = driver.findElement(By.xpath("//table[@id='example']/tbody/tr[" + i + "]/td[7]"))
							.getText();

					System.out.println("FirstName: " + FirstName);
					System.out.println("LastName: " + LastName);
					System.out.println("Position: " + Position);
					System.out.println("Office: " + Office);
					System.out.println("Salary: " + Salary);
					System.out.println("StartDate: " + StartDate);
					break;
				} else if (i == rows) {
					pageNumber++;
					driver.findElement(By.xpath("//div[@id='example_paginate']/span/a[" + pageNumber + "]")).click();
				}

			}
		}
	}
	
	@Test(priority=8)
	public void ValidateFileUpload() throws InterruptedException, AWTException{
		System.out.println("inside ValidateFileUpload");
		//extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		waitForPageToLoad();
		driver.navigate().to(objProperties.getProperty("FileUpload"));
		waitForPageToLoad();
		Actions obj = new Actions(driver);
		WebElement element = library_BusinessFunctions.FindElement(Orep.FileUploadBrowseButton);	
		obj.click(element).build().perform();
		Thread.sleep(5000);
		StringSelection objStringSelection = new StringSelection(System.getProperty("user.dir")+"\\src\\test\\resources\\Sample.jpg");
		Clipboard objClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		objClipboard.setContents(objStringSelection, null);
		Transferable objTransferable = objClipboard.getContents(null);
		if(objTransferable.isDataFlavorSupported(DataFlavor.stringFlavor)){
			try {
				System.out.println(objTransferable.getTransferData(DataFlavor.stringFlavor));
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Robot objRobot = new Robot();
		objRobot.delay(250);
		objRobot.keyPress(KeyEvent.VK_ENTER);
		objRobot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2000);
		objRobot.keyPress(KeyEvent.VK_CONTROL);
		objRobot.keyPress(KeyEvent.VK_V);
		Thread.sleep(2000);
		objRobot.keyRelease(KeyEvent.VK_V);
		objRobot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		objRobot.keyPress(KeyEvent.VK_ENTER);
		objRobot.keyRelease(KeyEvent.VK_ENTER);
		
	}
	
	
	@Test(priority=9)
	public void FileDownload() throws InterruptedException{
		System.out.println("inside FileDownload");
		//extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		waitForPageToLoad();
		driver.navigate().to(objProperties.getProperty("FileDownload"));
		waitForPageToLoad();
		library_BusinessFunctions.FindElement(Orep.FileDownload500kb).click();
		Thread.sleep(30000);
		File objFile = new File(System.getProperty("user.dir"));
		File[] AllFiles = objFile.listFiles();
		Boolean fileFound = false;
		File obj_File = null;
		for(File IndividualFile:AllFiles){
			String FileName = IndividualFile.getName();
			System.out.println("FileName:"+FileName);
			if(FileName.contains("file-sample")){
				fileFound=true;
				obj_File = new File(FileName);
			}
		}
		Assert.assertTrue(fileFound, "Downloaded File Not Found");
		obj_File.deleteOnExit();
	}
	
	@Test(priority=10)
	public void ValidateBrokenLinks(){
		System.out.println("inside ValidateBrokenLinks");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		waitForPageToLoad();
		driver.navigate().to(objProperties.getProperty("Links"));
		waitForPageToLoad();
		List<WebElement> All_links = library_BusinessFunctions.FindElements(Orep.AllLinks);
		System.out.println("total links avaiable is:"+All_links.size());
		for(int i=1 ; i<All_links.size();i++){
			String IndividualLink =All_links.get(i).getAttribute("href");
			System.out.println(IndividualLink);
			try {
				ValidatingLinks(IndividualLink);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Test(priority=11)
	public void ValidateBrokenLinksGmoOnline(){
		System.out.println("inside ValidateBrokenLinksGmoOnline");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		waitForPageToLoad();
		ValidateEnterGMoOnline();
		waitForPageToLoad();
		List<WebElement> All_links = library_BusinessFunctions.FindElements(Orep.AllLinks);
		System.out.println("total links avaiable is:"+All_links.size());
		for(int i=0 ; i<All_links.size();i++){
			String IndividualLink =All_links.get(i).getAttribute("href");
			System.out.println(IndividualLink);
			try {
				if(IndividualLink.startsWith("http")){
				ValidatingLinks(IndividualLink);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("inside beforeMethod");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) throws Exception {
		System.out.println("inside afterMethod");
		if(result.getStatus()==ITestResult.FAILURE){
			extent_Test.log(Status.FAIL, "TEST CASE Failed IS " + result.getName()); // to add name in extent report
			extent_Test.log(Status.FAIL, "TEST CASE Failed IS " + result.getThrowable()); // to add error/exception in extent report
			String screenshotPath = library_BusinessFunctions.screenShot(result.getName());
			extent_Test.addScreenCaptureFromPath(screenshotPath);//adding screen shot to the extent report
		}else if(result.getStatus()==ITestResult.SUCCESS){
			extent_Test.log(Status.PASS, "TEST CASE Passed Is " + result.getName());
			try {
				String screenshotPath = library_BusinessFunctions.screenShot(result.getName());
				extent_Test.addScreenCaptureFromPath(screenshotPath);//adding screen shot to the extent report
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(result.getStatus()==ITestResult.SKIP){
			extent_Test.log(Status.SKIP, "TEST CASE Skipped Is " + result.getName());
			try {
				String screenshotPath = library_BusinessFunctions.screenShot(result.getName());
				extent_Test.addScreenCaptureFromPath(screenshotPath);//adding screen shot to the extent report
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@BeforeClass
	public void beforeClass() {
		library_BusinessFunctions.StartExtentReport();
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
		extent_Reports.flush();
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
