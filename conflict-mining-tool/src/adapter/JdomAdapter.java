package adapter;

import java.io.File;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class JdomAdapter
{

	public static Document readXMLDocument(String xml)
	{
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = null;
		try 
		{
			doc = builder.build(new File(xml));
		} 
		catch (JDOMException jdome) 
		{
			System.err.println(jdome);
		} 
		catch (IOException ioe) 
		{
			System.err.println(ioe);
		}

		return doc;
	}

}
