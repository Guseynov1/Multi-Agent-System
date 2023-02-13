package lab4.Helper;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class XMLHelper {

    public static <T> void marshalAny (T information, String outPutFileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(information.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(information, new File(outPutFileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static <T> T unMarshalAny(Class<T> clazz,String outPutFileName){
        T object = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object obj = jaxbUnmarshaller.unmarshal(new File(outPutFileName));
            try {
                object = (T) obj;
            }
            catch (ClassCastException cce) {
                object = null;
                cce.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return  object;
    }
}
