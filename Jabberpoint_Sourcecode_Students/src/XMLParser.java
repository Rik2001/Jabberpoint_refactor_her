import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLParser implements Parser {
	
    /** Default API to use. */
    protected static final String DEFAULT_API_TO_USE = "dom";
    
    /** Names of xml tags of attributes */
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";
    
    /** Text of messages */
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";

	/**
	 * gets the specified element
	 * @param element File or element from which to read the tagName
	 * @param tagName name of the xml-tag which has to be read
	 * @return title name
	 */
	private String getTitle(Element element, String tagName) {
    	NodeList titles = element.getElementsByTagName(tagName);
    	return titles.item(0).getTextContent();
    	
    }

	/**
	 * gets a list of elements
	 * @param element element containing XML data from which to get all the tagNames
	 * @param tagName name of the xml-tag which has to be read
	 * @return a list of containing all tagName elements
	 */
	private NodeList getTagName(Element element, String tagName)
	{
		return element.getElementsByTagName(tagName);
	}

	/**
	 * load a Presentation from an XML file
	 * @param presentation	instance of a presentation to which the XML presentation will be assigned
	 * @param filename		namme of the XML-file which holds the presentation
	 * @throws IOException
	 */
	public void loadPresentation(Presentation presentation, String filename) throws IOException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();    	//create documentBuilder
			Document document = builder.parse(new File(filename)); //Create a JDOM document				//parse XML file into document variable
			Element xmlPresentation = document.getDocumentElement();									//enter the presentation element

			presentation.setTitle(getTitle(xmlPresentation, SHOWTITLE));								//get presentation title from XML file and append to Presentation object

			NodeList xmlSlides = getTagName(xmlPresentation, SLIDE);									//create a list of all the xmlSlide elements
			for (int slideNumber = 0; slideNumber < xmlSlides.getLength(); slideNumber++) 					//loop through all different xmlSlides
			{

				Element xmlSlide = (Element) xmlSlides.item(slideNumber);								//get slide[slideNumber] from xmlSlides and convert to Element
				Slide slide = new Slide();																//create a new slide
				slide.setTitle(getTitle(xmlSlide, SLIDETITLE));											//get SlideTitle from XML-element and appoint title to slide
				presentation.append(slide);																//append newly loaded slide to the presentation instance
				
				NodeList xmlSlideItems = getTagName(xmlSlide, ITEM);									//Load all xmlSlideItems from xmlSlide
				for (int itemNumber = 0; itemNumber < xmlSlideItems.getLength(); itemNumber++)				//loop through all different xmlSlideItems from a Slide
				{
					Element xmlSlideItem = (Element) xmlSlideItems.item(itemNumber);					//get xmlSlideItem[x]
					loadSlideItem(slide, xmlSlideItem);													//load slideItem
				}																					//exit slidItem loop
			}																					//exit slide loop
		}
		catch (IOException iox) {
			System.err.println(iox.toString());
		}
		catch (SAXException sax) {
			System.err.println(sax.getMessage());
		}
		catch (ParserConfigurationException pcx) {
			System.err.println(PCE);
		}	
	}

	/**
	 * load slide xmlSlideitem of a slide
	 * @param slide	The Slide to which the slideItem should be appended
	 * @param xmlSlideitem	The slideItem which
	 */
	protected void loadSlideItem(Slide slide, Element xmlSlideitem) {
		int level = 1;																		//default style level
		NamedNodeMap attributes = xmlSlideitem.getAttributes();								//get different attributes from the xmlSlideItem
		if(attributes != null)																//check if there are any attributes
		{
			String leveltext = attributes.getNamedItem(LEVEL).getTextContent();				//get level in string form
			if (leveltext != null) {														//check if leveltext is available
				try {
					level = Integer.parseInt(leveltext);									//try to convert level to int
				}
				catch(NumberFormatException x) {
					System.err.println(NFE);
				}
			}

			String type = attributes.getNamedItem(KIND).getTextContent();					//get slideItem type
			switch(type)																	//check on type
			{
				case TEXT:
					slide.append(new TextItem(level, xmlSlideitem.getTextContent()));		//append TextItem to slide
					break;

				case IMAGE:
					slide.append(new BitmapItem(level, xmlSlideitem.getTextContent()));		//append BitmapItem to slide
					break;

				default:
					System.err.println(UNKNOWNTYPE);										//print unknown slideItem
			}
		}
	}

	/**
	 * save a presentation to a file
	 * @param presentation	instance of the presentation that has to be saved
	 * @param filename		name of the saveFile
	 * @throws IOException
	 */
	public void savePresentation(Presentation presentation, String filename) throws IOException {
		PrintWriter xmlSaveFile = new PrintWriter(new FileWriter(filename));						//create saveFile

		xmlSaveFile.println("<?xml version=\"1.0\"?>");												//Add header element
		xmlSaveFile.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");

		xmlSaveFile.println("<presentation>");														//Open Presentation encapsulation and end line
		xmlSaveFile.println("<showtitle>" + presentation.getTitle() + "</showtitle>");				//add showTitle element to xml file

		for (int slideNumber=0; slideNumber<presentation.getSize(); slideNumber++) {				//loop through all different slides

			Slide slide = presentation.getSlide(slideNumber);										//get current slide
			xmlSaveFile.println("<slide>");															//open Slide encapsulation and end line
			xmlSaveFile.println("<title>" + slide.getTitle() + "</title>");							//add slide title element to xml file

			for (int itemNumber = 0; itemNumber< slide.getSize(); itemNumber++) {					//loops through all the slideitems

				SlideItem slideItem = slide.getSlideItem(itemNumber);								//gets current slideItem

				xmlSaveFile.print("<item kind=");													//open slideItem encapsulation
				if (slideItem instanceof TextItem)													//check if slideItem is a textItem
				{
					xmlSaveFile.print("\"text\" level=\"" + slideItem.getLevel() + "\">");			//add kind and level information
					xmlSaveFile.print( ( (TextItem) slideItem).getText());							//add text from textitem within an item
				}
				else if (slideItem instanceof BitmapItem)											//check if instance is a BitmapItem
				{
					xmlSaveFile.print("\"image\" level=\"" + slideItem.getLevel() + "\">");			//add kind and level information
					xmlSaveFile.print( ( (BitmapItem) slideItem).getName());						//add bitmapName within an item
				}
				else																				//if item is neither a textitem or bitmapitem
				{
					System.out.println("Ignoring " + slideItem);
				}

				xmlSaveFile.println("</item>");														//close slideItem encapsulation
			}																						//exit slideItem loop

			xmlSaveFile.println("</slide>");														//close slide encapsulation
		}																							//exit slide loop

		xmlSaveFile.println("</presentation>");														//close presentation encapsulation
		xmlSaveFile.close();																		//close xml file
	}
}
