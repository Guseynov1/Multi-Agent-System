package lab.lab4other.Auto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@XmlRootElement(name="Coefficients")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coefficients {

    /** Коэффициент, характеризующий генерацию ТЭС в текущий час */
    @XmlElement(name="A")
    private Double A;

    /** Коэффициент, характеризующий генерацию ВЭС в текущий час */
    @XmlElement(name="B1")
    private Double B1;

    /** Коэффициент, характеризующий генерацию ВЭС в текущий час */
    @XmlElement(name="B2")
    private Double B2;

    /** Коэффициент, характеризующий генерацию СЭС в текущий час */
    @XmlElement(name="C0")
    private Double C0;

    /** Коэффициент, характеризующий генерацию СЭС в текущий час */
    @XmlElement(name="C1")
    private Double C1;

    /** Коэффициент, характеризующий генерацию СЭС в текущий час */
    @XmlElement(name="C2")
    private Double C2;

    /** Коэффициент, характеризующий генерацию СЭС в текущий час */
    @XmlElement(name="C3")
    private Double C3;
}