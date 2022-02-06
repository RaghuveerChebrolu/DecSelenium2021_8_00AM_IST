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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

public class testNg5DataDriven extends library_BusinessFunctions {
	HashMap<String,String> testDataMap = new HashMap<String,String>();
	
	@Test(priority=9)
	public void DataDriven() throws InterruptedException, IOException{
		System.out.println("inside DataDriven");
		extent_Test = extent_Reports.createTest(new Object() {}.getClass().getEnclosingMethod().getName());
		driver.navigate().to(objProperties.getProperty("AutomationRegister"));
		waitForPageToLoad();
		try {
			File obj = new File(System.getProperty("user.dir")+"//src//test/resources//AutomationDemoSite.xlsx");
			FileInputStream  ObjFileInput = new FileInputStream(obj);
			XSSFWorkbook objXSSFWorkbook = new XSSFWorkbook(ObjFileInput);
			XSSFSheet objXSSFSheet = objXSSFWorkbook.getSheet("TestData");
			//take below classes for .xls format files
			//HSSFWorkbook
			//HSSFSheet obj = 
			int Rows = objXSSFSheet.getLastRowNum();
			System.out.println("Rows:"+Rows);
			for(int RowNumber = 1; RowNumber<=Rows ; RowNumber++){
				testDataMap = ReadExcelFile(objXSSFSheet,RowNumber);
				/*System.out.println("----------------------------");
				System.out.println(testDataMap.get("RunMode"));
				System.out.println(testDataMap.get("TestCaseName"));
				System.out.println(testDataMap.get("FirstName"));
				System.out.println(testDataMap.get("LastName"));
				System.out.println(testDataMap.get("Address"));
				System.out.println(testDataMap.get("Email Address"));
				System.out.println(testDataMap.get("Gender"));
				System.out.println(testDataMap.get("Hobbies"));
				System.out.println(testDataMap.get("Languages"));
				System.out.println(testDataMap.get("Skills"));
				System.out.println(testDataMap.get("Country"));
				System.out.println(testDataMap.get("SelectCountry"));
				System.out.println(testDataMap.get("DOB_MM"));
				System.out.println(testDataMap.get("Password"));
				System.out.println(testDataMap.get("confirm Password"));
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$");*/
				
				if(testDataMap.get("RunMode").equals("Yes")){
					library_BusinessFunctions.FindElement(Orep.DataDrivenFirstName).clear();
					library_BusinessFunctions.FindElement(Orep.DataDrivenFirstName).sendKeys(testDataMap.get("FirstName").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDrivenLastName).clear();
					library_BusinessFunctions.FindElement(Orep.DataDrivenLastName).sendKeys(testDataMap.get("LastName").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDrivenAddress).clear();
					library_BusinessFunctions.FindElement(Orep.DataDrivenAddress).sendKeys(testDataMap.get("Address").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDrivenEmail).clear();
					library_BusinessFunctions.FindElement(Orep.DataDrivenEmail).sendKeys(testDataMap.get("Email Address").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDrivenPhone).clear();
					library_BusinessFunctions.FindElement(Orep.DataDrivenPhone).sendKeys(testDataMap.get("PhoneNumber").trim());
					
					if(testDataMap.get("Gender").trim().equalsIgnoreCase("male")){
						library_BusinessFunctions.FindElement(Orep.DataDrivenGenderMale).click();
					}else {
						library_BusinessFunctions.FindElement(Orep.DataDrivenGenderFemale).click();
					}
					
					
					if(RowNumber>1){
						Boolean cricket = library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesCricket).isSelected();
						if(cricket==true){
							library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesCricket).click();
						}
						Boolean movies = library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesMovies).isSelected();
						if(movies==true){
							library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesMovies).click();
						}
						Boolean hockey = library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesHockey).isSelected();
						if(hockey==true){
							library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesHockey).click();
						}
						String hobbies = testDataMap.get("Hobbies").trim();
						//Cricket & Hockey
						String[] AllHobbies = hobbies.split("&");
						for(String hobby :AllHobbies ){
							if(hobby.equalsIgnoreCase("cricket")){
								library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesCricket).click();
							}else if(hobby.equalsIgnoreCase("movies")){
								library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesMovies).click();
							}else if (hobby.equalsIgnoreCase("hockey")){
								library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesHockey).click();
							}
						}
					}else{
						String hobbies = testDataMap.get("Hobbies").trim();
						//Cricket & Hockey
						String[] AllHobbies = hobbies.split("&");
						for(String hobby :AllHobbies ){
							if(hobby.equalsIgnoreCase("cricket")){
								library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesCricket).click();
							}else if(hobby.equalsIgnoreCase("movies")){
								library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesMovies).click();
							}else if (hobby.equalsIgnoreCase("hockey")){
								library_BusinessFunctions.FindElement(Orep.DataDrivenHobbiesHockey).click();
							}
						}
					}
					
					ScrollDown(500);
					
					if(RowNumber>1){
						library_BusinessFunctions.FindElement(Orep.DataDrivenCloseIconLanguages).click();
					}
					
					library_BusinessFunctions.FindElement(Orep.DataDrivenLanguages).click();
					List<WebElement> AllLanguages =library_BusinessFunctions.FindElements(Orep.DataDrivenAllLanguages);
					SelectValueFromDropDown(AllLanguages,testDataMap.get("Languages").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDrivenSkillsField).click();
					
					library_BusinessFunctions.FindElement(Orep.DataDriven_Skills).click();
					List<WebElement> AllSkills =library_BusinessFunctions.FindElements(Orep.DataDriven_AllSkills);
					SelectValueFromDropDown(AllSkills,testDataMap.get("Skills").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDrivenSelectCountry).click();
					library_BusinessFunctions.FindElement(Orep.DataDrivenTextBoxSelectCountry).sendKeys(testDataMap.get("SelectCountry"));
					
					PressEnterKey();
					
					library_BusinessFunctions.FindElement(Orep.DataDriven_DOB_YY).click();
					List<WebElement> AllYears =library_BusinessFunctions.FindElements(Orep.DataDriven_ALLYearsDOB_YY);
					SelectValueFromDropDown(AllYears,testDataMap.get("DOB_YY").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDriven_DOB_MM).click();
					List<WebElement> AllMonths =library_BusinessFunctions.FindElements(Orep.DataDriven_ALLMonthsDOB_MM);
					SelectValueFromDropDown(AllMonths,testDataMap.get("DOB_MM").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDriven_DOB_DD).click();
					List<WebElement> AllDays =library_BusinessFunctions.FindElements(Orep.DataDriven_ALLDaysDOB_DD);
					SelectValueFromDropDown(AllDays,testDataMap.get("DOB_DD").trim());
					
					library_BusinessFunctions.FindElement(Orep.DataDriven_Pwd).clear();
					library_BusinessFunctions.FindElement(Orep.DataDriven_Pwd).sendKeys(testDataMap.get("Password"));
					
					library_BusinessFunctions.FindElement(Orep.DataDriven_ConfirmPWD).clear();
					library_BusinessFunctions.FindElement(Orep.DataDriven_ConfirmPWD).sendKeys(testDataMap.get("confirm Password"));
					
					FileOutputStream objFileOutput = new FileOutputStream(obj);
					WriteToExcelFile(objXSSFWorkbook,objXSSFSheet,RowNumber);
					objXSSFWorkbook.write(objFileOutput);
				}else {
					 int count = RowNumber+1;
					System.out.println("RunMode in test data excel file is not marked as Yes for row number :"+count);
				}

				
			}
			objXSSFWorkbook.close();
			ObjFileInput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//library_BusinessFunctions.FindElement(Orep.FileDownload500kb).click();
		
	}

	public HashMap<String, String> ReadExcelFile(XSSFSheet objXSSFSheet, int rowNumber) {
		// TODO Auto-generated method stub
		DataFormatter ObjFormatter = new DataFormatter();
		testDataMap.put("RunMode", objXSSFSheet.getRow(rowNumber).getCell(0).getStringCellValue());
		testDataMap.put("TestCaseName", objXSSFSheet.getRow(rowNumber).getCell(1).getStringCellValue());
		testDataMap.put("FirstName", objXSSFSheet.getRow(rowNumber).getCell(2).getStringCellValue());
		testDataMap.put("LastName", objXSSFSheet.getRow(rowNumber).getCell(3).getStringCellValue());
		testDataMap.put("Address", objXSSFSheet.getRow(rowNumber).getCell(4).getStringCellValue());
		testDataMap.put("Email Address", objXSSFSheet.getRow(rowNumber).getCell(5).getStringCellValue());
		testDataMap.put("PhoneNumber", ObjFormatter.formatCellValue(objXSSFSheet.getRow(rowNumber).getCell(6)));
		testDataMap.put("Gender", objXSSFSheet.getRow(rowNumber).getCell(7).getStringCellValue());
		testDataMap.put("Hobbies", objXSSFSheet.getRow(rowNumber).getCell(8).getStringCellValue());
		testDataMap.put("Languages", objXSSFSheet.getRow(rowNumber).getCell(9).getStringCellValue());
		testDataMap.put("Skills", objXSSFSheet.getRow(rowNumber).getCell(10).getStringCellValue());
		testDataMap.put("Country", objXSSFSheet.getRow(rowNumber).getCell(11).getStringCellValue());
		testDataMap.put("SelectCountry", objXSSFSheet.getRow(rowNumber).getCell(12).getStringCellValue());
		testDataMap.put("DOB_YY", ObjFormatter.formatCellValue(objXSSFSheet.getRow(rowNumber).getCell(13)));
		testDataMap.put("DOB_MM", objXSSFSheet.getRow(rowNumber).getCell(14).getStringCellValue());
		testDataMap.put("DOB_DD",ObjFormatter.formatCellValue(objXSSFSheet.getRow(rowNumber).getCell(15)));
		testDataMap.put("Password", objXSSFSheet.getRow(rowNumber).getCell(16).getStringCellValue());
		testDataMap.put("confirm Password", objXSSFSheet.getRow(rowNumber).getCell(17).getStringCellValue());	
		return testDataMap;
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
		System.out.println("inside beforeClass");
		library_BusinessFunctions.StartExtentReport();
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
