package web;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import web.Resources.Core.base;
import web.Resources.pageObject.HomePage;


public class ProductSearch extends base {

	@BeforeTest
	public void initilize() throws IOException
	{
		driver= intializeDriver();
		//Navigation
		driver.get(getBaseUrl());
	}
	@AfterTest
	public void cleanUp()
	{
		driver.close();
	}
	
	@Test
	public void FirstTest() throws IOException
	{
		
		HomePage hp=new HomePage(driver);
		
		//hp.getSearchBar().sendKeys("iPhone"+ Keys.RETURN );
		cjsSendKeys(hp.getSearchBar(), "iPhone");
		cjsClick(hp.getSearchIcon());
			
	}
	
	
}
