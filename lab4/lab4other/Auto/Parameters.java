package lab.lab4other.Auto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@XmlRootElement(name="Parameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameters {

    /** Потребляемая ЭЭ в текущий час */
    @XmlElement(name="L")
    private List<Double> firstPower;
    @XmlElement(name="L")
    private List<Double> secondPower;
    @XmlElement(name="L")
    private List<Double> thirdPower;
    @XmlElement
    private String distributer;

}