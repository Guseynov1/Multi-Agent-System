package lab.lab4new.Auto;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.SneakyThrows;

import java.io.File;

public class ConfigReader {

    @SneakyThrows
    public static Parameters getParameters(String name) {
        String cfgPath = "src/main/resources/" + name + ".xml";
        JAXBContext jaxbContext = JAXBContext.newInstance(Parameters.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Parameters) unmarshaller.unmarshal(new File(cfgPath));
    }
}
