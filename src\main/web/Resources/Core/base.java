package web.Resources.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.internal.junit.ArrayAsserts;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import okhttp3.internal.Version;

public class base {
	//Not making the Driver as static so that I can have Parellel Execution
	protected WebDriver driver;
	public  String testName;
	public String testClass;
	private String screenshotDir="//Users//mandeepdhiman//eclipse-workspace//SeleniumJava//TestAutomation//Screenshots//";
	Properties propFile;
	FileInputStream oFIS;
	JavascriptExecutor js;
	WebDriverWait wd;
	DesiredCapabilities dc;
	static final Logger log=LogManager.getLogger(base.class.getName());
	private int screenshotCount=1;
	
	
	public WebDriver intializeDriver() throws IOException
	{
		 propFile=new Properties();
		 //Create Input File System to Load the properties
		 oFIS = new FileInputStream("/Users/mandeepdhiman/eclipse-workspace/SeleniumJava/TestAutomation/src\\main/web/Config/TestConfig.ini");
		//Load Properties
		propFile.load(oFIS);
		
		dc=new DesiredCapabilities();
		
		if(propFile.getProperty("RemoteWebDriver").toString().equals("1"))
		{	
			
			if (propFile.getProperty("browser").toString().toUpperCase().equals("CHROME")) 
				dc.setBrowserName(BrowserType.CHROME);		
			else if (propFile.getProperty("browser").toString().toUpperCase().equals("FIREFOX"))  
				dc.setBrowserName(BrowserType.FIREFOX);
			else if (propFile.getProperty("browser").toString().toUpperCase().equals("IE")) 
				dc.setBrowserName(BrowserType.IE);
			else if (propFile.getProperty("browser").toString().toUpperCase().equals("EDGE")) 
				dc.setBrowserName(BrowserType.EDGE);
			else
				dc.setBrowserName(BrowserType.CHROME);	
			
			driver= new RemoteWebDriver(new URL(propFile.getProperty("RemoteHub").toString()), dc);
		}
		else	
		{

			if (propFile.getProperty("browser").toString().toUpperCase().equals("CHROME")) {				
				System.setProperty("webdriver.chrome.driver", propFile.getProperty("DriverPath")+"/chromedriver");
				driver=new ChromeDriver();
			}
			else if (propFile.getProperty("browser").toString().toUpperCase().equals("FIREFOX"))  {
				System.setProperty("webdriver.gecko.driver", propFile.getProperty("DriverPath")+"/geckodriver");
				driver=new FirefoxDriver();
			}
			else if (propFile.getProperty("browser").toString().toUpperCase().equals("IE")) {
				System.setProperty("webdriver.internetexplorer.driver", propFile.getProperty("DriverPath")+"/internetexplorerdriver");
				driver=new InternetExplorerDriver();
			}
			else if (propFile.getProperty("browser").toString().toUpperCase().equals("EDGE")) {
				System.setProperty("webdriver.edge.driver", propFile.getProperty("DriverPath")+"/edgedriver");
				driver=new EdgeDriver();
			}
			else {
				dc.setBrowserName(BrowserType.CHROME);	
				System.setProperty("webdriver.chrome.driver", propFile.getProperty("DriverPath")+"/chromedriver");
				driver=new ChromeDriver();
			}
		}
		log.info("intializeDriver");
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
	
	
	/* Function: getBaseUrl
	 * Function To read BaseURL from Property File
	 * Return: URL from the Property File
	 */
	
	public String getBaseUrl()
	{
		
		return propFile.getProperty("BaseURL").toString();
	}
	/* Function: cjsSendKeys
	 * Function to Send the text to Webelement using executeJavaScript
	 * oWebelement: Webelement to be used
	 * text: String text to Enter. Selenium Keys Class is Not Supported
	 */
	protected void cjsSendKeys(WebElement oWebelement,String text)
	{
		try
		{
			log.info("Sending keys unsing Js");
			wd.until(ExpectedConditions.visibilityOf(oWebelement));
			js.executeScript("arguments[0].value=arguments[1]", oWebelement,text);
		}
		catch (Exception e){
			log.error("Sending keys unsing Js Failed");
			System.out.println("WebElement Not Found:"+oWebelement.toString());
		}
		
	}

	/* Function: cjsClick
	 * Function to Click Webelement using executeJavaScript
	 * oWebelement: Webelement to be used	
	 */
	protected void cjsClick(WebElement oWebelement)
	{
		try
		{
			log.info("Clicking unsing Js");
			wd.until(ExpectedConditions.visibilityOf(oWebelement));	
			js.executeScript("arguments[0].click()", oWebelement);
		}
		catch (Exception e){
			log.error("Clicking unsing Js Failed");
			System.out.println("WebElement Not Found:"+oWebelement.toString());
		}
		
	}
	
	/* Function: cjsScrollToElement
	 * Function to Scrool to Webelement using executeJavaScript
	 * oWebelement: Webelement to be used	
	 */
	protected void cjsScrollToElement(WebElement oWebelement)
	{
		try
		{
			log.info("ScrollToElement unsing Js");
			wd.until(ExpectedConditions.visibilityOf(oWebelement));			
			js.executeScript("arguments[0].scrollIntoView", oWebelement);
		}
		catch (Exception e){
			log.error("ScrollToElement unsing Js Failed");
			System.out.println("WebElement Not Found:"+oWebelement.toString());
		}
		
	}
	
	/* Function to Select List by partial Text
	 * lst: Select Object
	 * text: Partial or Full text to be selected
	 */
	public void selectLstElementByPartialText (Select lst,String text )
	{
		List<WebElement> optionlist =	lst.getOptions();
		for (WebElement webElement : optionlist) {
			String optionText=webElement.getText();
			if(optionText.contains(text))
			{
				lst.selectByVisibleText(optionText);
				break;
			}
		}
	
	}
	
	/* Functionto Capture the page Screenshot
	 * testName: Name of the test case
	 * spath= Path to Store the Screenshot
	 */
	public  void capturePageScreenshot(String spath ) throws IOException
	{
		log.info("Capturing the Page Screenshot");
		File scFile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Copy the File Content to Default Location
		String defaultPath=screenshotDir+this.getClass().getName()+"//";
		File sdFile= new File(spath);
		try
		{
			if (!(sdFile.isAbsolute()))
			{
				defaultPath= defaultPath+spath;
				sdFile =new File(defaultPath);
			}	
				
			FileUtils.copyFile(scFile, sdFile);
			screenshotCount++;
			log.info("Screenshot Captured:"+sdFile);
		}
		catch(IOException e)
		{
			log.error("Screenshot Path is Not correct. Path must be an absolute Path. Actual Path:"+sdFile);
			
		}
	
		
	}
	
	/* Function to Capture the page Screenshot
	 * testName: Name of the test case
	 * spath= Path to Store the Screenshot
	 */
	public  void capturePageScreenshot() throws IOException
	{
		log.info("Capturing the Page Screenshot");
		File scFile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Copy the File Content to Default Location
		File sdFile= new File(screenshotDir+"//Screenshot"+screenshotCount + ".png");
		FileUtils.copyFile(scFile, sdFile);
		screenshotCount++;
		
	}
	
	public void setScreenshotDirectory(String dirPath)
	{
		if ((dirPath != null) && !(dirPath.isEmpty()))
		{
			screenshotDir=dirPath;	
			log.info("Screenshot Directory has been set to : " +dirPath);
		}
		else
			log.error ("Screenshot Path Cannot be null or Empty");
	}
	
	public void loadTestData(String testName) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		try {
			FileInputStream xmlFile=new FileInputStream("/Users/mandeepdhiman/eclipse-workspace/SeleniumJava/TestAutomation/src\\main/web/Resources/Core/Data.xml");
			
			DocumentBuilder db=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//Parsing the XML to DOM 
			Document xmlDom=	db.parse(xmlFile);
			// Chreating the Xpath Object to fetch the data from the DOM using xPath
			XPath xPathObject=  XPathFactory.newInstance().newXPath();
			Node testCaseNode=	(Node) xPathObject.evaluate("/TestData/Web/TEST_NAME[@Name=\""+testName+"\"]",xmlDom,XPathConstants.NODE);
			if (testCaseNode != null)  
			{
				log.info("Test Data Found");
				NodeList childNodes= testCaseNode.getChildNodes();
				for (int i = 0; i < childNodes.getLength(); i++) {
					if (childNodes.item(i).getNodeType()==Node.ELEMENT_NODE)
						log.info(childNodes.item(i).getNodeName() +" : "+	childNodes.item(i).getTextContent());
						//#TO DO: Need to return this data to the calling function.  
				}
			}
			
			else
				log.error("Test Data Not Found For Test:"+testName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
