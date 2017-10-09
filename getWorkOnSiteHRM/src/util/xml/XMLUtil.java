package util.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;

public class XMLUtil {

	public static void objectToXML(String filePath, Object object) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(object, FileUtils.getFile(filePath));
//			jaxbMarshaller.marshal(object, System.out);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void objectToXML(String filePath, String encoding, Object object) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			jaxbMarshaller.marshal(object, FileUtils.getFile(filePath));
//			jaxbMarshaller.marshal(object, System.out);
		} catch (Exception e) {
			throw e;
		}
	}

	public static Object xmlToObject(String filePath, Object object) throws Exception {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			object = jaxbUnmarshaller.unmarshal(FileUtils.getFile(filePath));
		} catch (Exception e) {
			throw e;
		}
		return object;
	}
}
