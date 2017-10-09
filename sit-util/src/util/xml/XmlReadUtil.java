package util.xml;

//Read Xml
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//
/**
 * Description: อ่านค่า  xml file
 * @Author : SD
 * @Version : 1.0
 * @Create date: 09/02/2012
 * 
 */
public class XmlReadUtil {
	private static Document doc;
	private String strfile;
	public XmlReadUtil(String strfile){
		try{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(new File(strfile));
			this.strfile=strfile;
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * อ่านค่า Xml File
	 * 
	 * @Author : SD
	 * @param parent : parent node 
	 * @param child : child node  
	 * @return String : value node
	 */
	public  String doRead(String Parent, String child) {
		try {


			// normalize text representation
			doc.getDocumentElement().normalize();


			NodeList listOfParam = doc.getElementsByTagName(Parent);
			int totalParam = listOfParam.getLength();


			for (int s = 0; s < listOfParam.getLength(); s++) {
				Node dbtype = listOfParam.item(s);
				if (dbtype.getNodeType() == Node.ELEMENT_NODE) {
					Element dbparamelement = (Element) dbtype;
					NodeList listconnpool = dbparamelement.getElementsByTagName(child);


					Element connpoolelement = (Element) listconnpool.item(0);
					NodeList listchildconnpool = connpoolelement.getChildNodes();

					return ((Node)listchildconnpool.item(0)).getNodeValue().trim();

				}
			}

		}catch(Exception e){
			System.out.println(e);
		}

		return "";
	}
	
	public List<String> doRead2(String Parent, String child) throws Exception {
		List result = new ArrayList();
		try {
			doc.getDocumentElement().normalize();
			NodeList listOfParam = doc.getElementsByTagName(Parent);
			for (int s = 0; s < listOfParam.getLength(); s++) {
				Node dbtype = listOfParam.item(s);
				if (dbtype.getNodeType() == Node.ELEMENT_NODE) {
					Element dbparamelement = (Element) dbtype;
					NodeList listconnpool = dbparamelement.getElementsByTagName(child);
					Element connpoolelement = (Element) listconnpool.item(0);
					NodeList listchildconnpool = connpoolelement.getChildNodes();
					result.add(((Node) listchildconnpool.item(0)).getNodeValue().trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}
}
