package lab.lab4other.Master;

import lombok.Data;

import jakarta.xml.bind.annotation.*;
import java.util.List;
@Data
@XmlRootElement(name="cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class Meeting {
    @XmlElement(name="definition")
    @XmlElementWrapper(name="agents")
    private List<Definition> definitions;
}
