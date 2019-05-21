package web;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import web.Resources.Core.base;



public class XmlTest extends base {
	static final Logger log=LogManager.getLogger(XmlTest.class.getName());
	//Declaring the webdriver in the Class because I am planning to run the Parellel Class not methods.
	//I am assuming that that one Test Class will have all the related test cases and Test Methods should not run in Parellel.
	
	
	@Test
	public void SecondTest() throws IOException, XPathExpressionException, ParserConfigurationException, SAXException
	{
		System.out.println("Found Password:"+getData("FirstTest","Password"));
		System.out.println("Found Password:"+getData("FirstTest","Password"));
			
	}
}
