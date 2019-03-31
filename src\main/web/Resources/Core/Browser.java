package web.Resources.Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

//Try to mimic the behavior of Static classes in Java. Java can have only Static classes nested inside function.
// Idea was to create a static class to manage browser. I have not given thought on pros and corns on this approach but will like to explore.
public final class Browser {
	
	public static WebDriver driver;
	public static JavascriptExecutor js;
	public static WebDriverWait wd;
	
	private Browser()
	{
		//TODO
	}

	public static WebDriver initialize() throws Exception
	{
		 Properties propFile = new Properties();
		 //Create Input File System to Load the properties
		 FileInputStream oFIS = new FileInputStream("/Users/mandeepdhiman/eclipse-workspace/SeleniumJava/CanadaITAutomation/src/main/java/Onlinebanking/Config/TestConfig.ini");
		//Load Properties
		propFile.load(oFIS);
		if (propFile.getProperty("browser").equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", propFile.getProperty("DriverPath")+"/chromedriver");
			driver= new ChromeDriver();
		}
		if (driver!=null)
		{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
		//Setting the JavaScript Executor
		js=(JavascriptExecutor)driver;
		//Set Web driver Wait
		wd=new WebDriverWait(driver, 5000);
		
		return driver;
	}
}
