package web;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import web.Resources.Core.base;
import web.Resources.pageObject.HomePage;

@Listeners({web.Resources.Core.TestNGListener.class})

public class ProductSearch2 extends base {
	static final Logger log=LogManager.getLogger(ProductSearch.class.getName());
	WebDriver driver;
	@BeforeTest
	public void initilize() throws IOException
	{
		log.info("@BeforeMethod:From Product Search");
		driver= intializeDriver();
		
	}
	@AfterTest
	public void cleanUp()
	{
		log.info("@AfterMethod:From Product Search");
		driver.close();
		driver=null;
	}
	
	@Test
	public void FirstTest() throws IOException
	{
		log.info("FirstTest Thread: "+Thread.currentThread().getId());
		//Navigation
		driver.get(getBaseUrl());
		HomePage hp=new HomePage(driver);
		
		//hp.getSearchBar().sendKeys("iPhone"+ Keys.RETURN );
		cjsSendKeys(hp.getSearchBar(), "iPhone");
		cjsClick(hp.getSearchIcon());

		
	
	}
	
	@Test
	public void SecondTest() throws IOException
	{
		log.info("SecondTest Thread: "+Thread.currentThread().getId());
		//Navigation
		driver.get(getBaseUrl());
		HomePage hp=new HomePage(driver);
		
		//hp.getSearchBar().sendKeys("iPhone"+ Keys.RETURN );
		cjsSendKeys(hp.getSearchBar(), "iPhone");
		cjsClick(hp.getSearchIcon());
			
	}
}
