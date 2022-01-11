package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
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
		// implicit wait : gloabal waiting mechanism applicable for all web
		// elements
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
}
