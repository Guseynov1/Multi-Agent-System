package lab.lab4other.Master;

import lombok.Data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Definition {
	@XmlAttribute
	private String agent;
	@XmlAttribute
	private String clazz;
	@XmlAttribute
	private List<Double> file;

}
