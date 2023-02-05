package lab.lab3.container;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
@Slf4j
public class xmlHandler {

    public static String[][] unMarshalAny(String actualAgent) {
        String[][] neighborParameters = new String[0][];
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Config object = (Config) jaxbUnmarshaller.unmarshal(new File(actualAgent));
            int i = 0;
            int num = object.getNodes().size();
            neighborParameters = new String[num][2];
            for (NodeAgent nodeAgent : object.getNodes()){
                neighborParameters[i][0] = nodeAgent.getAgentName();
                neighborParameters[i][1] = String.valueOf(nodeAgent.getWeight());
                i++;
            }
        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
        return neighborParameters;
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
