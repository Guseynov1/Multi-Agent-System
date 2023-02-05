package lab.lab3.container;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeAgent {
    @XmlAttribute
    private String AgentName;
    @XmlAttribute
    private Double Weight;
    @Override
    public String toString() {
        return String.format("Neighbor -> AgentName %s, Weight %f", AgentName, Weight);
    }
}
