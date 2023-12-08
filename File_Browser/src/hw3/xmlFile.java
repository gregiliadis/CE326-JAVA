/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author uesr1
 */
public class xmlFile {
    String xmlFilePath;
    String paths[];
    
    public xmlFile(String xmlFilePath, String paths[]){
       this.xmlFilePath = xmlFilePath;
       this.paths = paths;
    }
    
    public void xmlWriter() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("favourites");
            doc.appendChild(rootElement);
            
            //path elements
            for( String str : paths){  
                Element favFile = doc.createElement("path");
                favFile.appendChild(doc.createTextNode(str));
                rootElement.appendChild( favFile );
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

	} 
        catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	} 
        catch (TransformerException tfe) {
		tfe.printStackTrace();
	}
    }
    
    public void SetPaths( String paths[] ){
        this.paths = paths;
    }
    public String[] xmlReader() {
        String Filepaths[] = null;
        
        try {
            File XmlFile = new File( this.xmlFilePath );
            if( !XmlFile.exists() ){
                Filepaths = new String[1];
                Filepaths[0] = System.getProperty("user.home");
                System.out.println(Filepaths[0]);
                return Filepaths;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(XmlFile);
			
            doc.getDocumentElement().normalize();
            
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("path");
            System.out.println("-------------- " + nList.getLength() +" ------------------");
            Filepaths = new String[nList.getLength()];
            
            for (int temp = 0; temp < nList.getLength(); temp++) {
		Node nNode = nList.item(temp);		
		System.out.println("\nCurrent Element :" + nNode.getNodeName());		
		System.out.println("File path: " + nNode.getTextContent() );
                Filepaths[temp] =  nNode.getTextContent();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
     
        this.paths = Filepaths;
        
        return(Filepaths);
    }
    
}
