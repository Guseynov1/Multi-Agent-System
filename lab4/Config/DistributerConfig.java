package lab4.Config;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name="DistributerConfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class DistributerConfig {
    @XmlElement(name = "ProducersName")
    private String ProducersName;
    @XmlElement(name = "TopicName")
    private String TopicName;

}
