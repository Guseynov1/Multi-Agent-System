package lab4.Config;


import jakarta.xml.bind.annotation.*;
import lab4.Helper.XMLHelper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "cfg")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coefficients {
    public static void main(String[] args) {
        Coefficients coefficients = new Coefficients();
        List<Double> B = new ArrayList<>(), C = new ArrayList<>();
        B.add(7.2);
        B.add(4.8);
        C.add(-86.884);
        C.add(22.344);
        C.add(-1.450);
        C.add(0.027);
        coefficients.setA(9.6);
        coefficients.setB(B);
        coefficients.setC(C);
        XMLHelper.marshalAny(coefficients, "src/main/resources/config.xml");
    }
    @XmlElement(name="A")
    private double A;
    @XmlElementWrapper(name="Bs")
    @XmlElement(name="B")
    private List<Double> B;
    @XmlElementWrapper(name="Cs")
    @XmlElement(name="C")
    private List<Double> C;
}