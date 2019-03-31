package web.Resources.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
	private WebDriver driver;
	
	private By searchBar=	By.id("twotabsearchtextbox");
	By signIn=By.xpath("//*[text()='Hello, Sign in']");
	By header=	By.xpath("//*[text()='Welcome to Amazon Canada']");
	By searchIcon=By.xpath("//input[@value='Go']");
	
	public HomePage(WebDriver driver)
	{
		this.driver=driver;
	}
	public WebElement getSearchBar() {
		return driver.findElement(searchBar);
	}
	
	public WebElement getSignIN() {
		return driver.findElement(signIn);
	}
	
	public WebElement getHeader() {
		return driver.findElement(header);
	}
	
	public WebElement getSearchIcon() {
		return driver.findElement(searchIcon);
	}
	

}
