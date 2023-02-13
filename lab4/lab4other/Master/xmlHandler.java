package lab.lab4other.Master;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import jakarta.xml.bind.Unmarshaller;
import java.io.File;

public class xmlHandler {
    public static <T> T unMarshalAny(Class<T> clazz, String outPutFileName) {
        T object = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            try {
                object = (T) jaxbUnmarshaller.unmarshal(new File(outPutFileName));;
            } catch (ClassCastException cce) {
                cce.printStackTrace();
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static <T> void marshalAny(Class<T> clazz, T information, String outPutFileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(information, new File(outPutFileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
