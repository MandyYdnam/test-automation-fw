package web.Resources.Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/*
 * Class to read and write the data.
 */
public class DataFactory {
	Hashtable<String, String> oData;
	static final Logger log=LogManager.getLogger(base.class.getName());
	/*
	 * Function to Read the Test Data from XML Source File
	 * Return: Hash Table with loaded Test Data
	 */
	public DataFactory()
	{
		oData=null;
	}
	private void loadTestData(String testName) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
	{
		oData=new Hashtable<>();
		
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
					{
						log.info(childNodes.item(i).getNodeName() +" : "+	childNodes.item(i).getTextContent());
						//#TO DO: Need to return this data to the calling function.  
						oData.put(childNodes.item(i).getNodeName(), childNodes.item(i).getTextContent());
					}
				
				}
			}
			
			else
				log.fatal("Test Data Not Found For Test:"+testName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public String getData(String testCaseName,String datafield)
	{
		//Load the Data if already not loaded
		if (oData==null) 
		{
			try {
				loadTestData(testCaseName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(oData.containsKey(datafield))
			return oData.get(datafield);
		else
			return null;
	}
	
	

}
