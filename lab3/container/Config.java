package lab.lab3.container;


import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;
@Data
@XmlRootElement(name="cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class Config {
    @XmlElement
    private  String name;
    @XmlElementWrapper(name="neighbors")
    @XmlElement(name="neighbor")
    private List<NodeAgent> nodes;
}
