package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class library_BusinessFunctions {
	public static WebDriver driver;
	public static Properties objProperties = new Properties();
	
	public static void ReadPropertyFile() throws IOException {
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

	public static void LaunchBrowser() {
		String Browser = objProperties.getProperty("browser");
		switch (Browser) {
		case "IE":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		case "chrome":
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
		// implicit wait : gloabal waiting mechanism applicable for all web
		// elements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public static WebElement FindElement(String OrepLocator){
		By search=null;
		System.out.println(OrepLocator); 
		String locator = OrepLocator.split("&")[0];
		String value = OrepLocator.split("&")[1];
		System.out.println(locator);
		System.out.println(value);
		if(locator.equals("name")){
			search=By.name(value);
		}else if (locator.equals("id")){
			search=By.id(value);
		}else if (locator.equals("xpath")){
			search=By.xpath(value);
		}else if (locator.equals("tagName")){
			search=By.tagName(value);
		}else if (locator.equals("className")){
			search=By.className(value);
		}else if (locator.equals("partialLinkText")){
			search=By.partialLinkText(value);
		}else if (locator.equals("cssSelector")){
			search=By.cssSelector(value);
		}else if (locator.equals("linkText")){
			search=By.linkText(value);
		}
		return driver.findElement(search);
	}
	
	public static List<WebElement> FindElements(String OrepLocator){
		By search=null;
		System.out.println(OrepLocator); 
		String locator = OrepLocator.split("&")[0];
		String value = OrepLocator.split("&")[1];
		System.out.println(locator);
		System.out.println(value);
		if(locator.equals("name")){
			search=By.name(value);
		}else if (locator.equals("id")){
			search=By.id(value);
		}else if (locator.equals("xpath")){
			search=By.xpath(value);
		}else if (locator.equals("tagName")){
			search=By.tagName(value);
		}else if (locator.equals("className")){
			search=By.className(value);
		}else if (locator.equals("partialLinkText")){
			search=By.partialLinkText(value);
		}else if (locator.equals("cssSelector")){
			search=By.cssSelector(value);
		}else if (locator.equals("linkText")){
			search=By.linkText(value);
		}
		return driver.findElements(search);
	}
}
