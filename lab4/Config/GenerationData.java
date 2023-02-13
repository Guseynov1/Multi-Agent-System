package lab4.Config;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import lab4.Helper.TimeHelper;
import lab4.Helper.XMLHelper;
import lombok.extern.slf4j.Slf4j;
import lab4.Config.Parameters.Color;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class GenerationData extends TickerBehaviour {

    Data producerDataТЭС, producerDataВЭС, producerDataСЭС;
    Double[] production = new Double[24];


    public GenerationData(Agent agent, long period, Data producerDataТЭС, Data producerDataВЭС, Data producerDataСЭС) {
        super(agent, period);
        this.producerDataТЭС = producerDataТЭС;
        this.producerDataВЭС = producerDataВЭС;
        this.producerDataСЭС = producerDataСЭС;
    }

    @Override
    protected void onTick() {
        Coefficients coefficients = XMLHelper.unMarshalAny(Coefficients.class, "src/main/resources/config.xml");
        switch (getAgent().getLocalName()) {
            case "ТЭС" -> {
                producerDataТЭС.getProducerLoad().put(getAgent().getAID(),coefficients.getA());
                log.info(Color.BLUE + "{} storage is {}" + Color.RESET, "ТЭС", producerDataТЭС.getProducerLoad().get(getAgent().getAID()));
            }
            case "ВЭС" -> {
                System.out.println("********************************************************************************");
                // закон Гаусса (нормальное распределение)
                double energy = (1 / coefficients.getB().get(1) * Math.sqrt(2 * Math.PI) *
                        Math.exp(-(Math.pow((TimeHelper.getActualHour() - coefficients.getB().get(0)), 2) / (2 * Math.pow(coefficients.getB().get(1), 2))))) * 35;
                producerDataВЭС.getProducerLoad().put(getAgent().getAID(), energy);
                log.info(Color.BLUE + "{} storage is {}" + Color.RESET, "ВЭС", producerDataВЭС.getProducerLoad().get(getAgent().getAID()));
            }
            case "СЭС" -> {
                List<Double> C = coefficients.getC();
                IntStream.range(0, 24).forEach(i -> {
                    if (i <= 5 || i >= 19) production[i] = 0.0;
                    else {
                        production[i] = 0.0;
                        IntStream.range(0, C.size()).forEach(index -> production[i] += C.get(index) * Math.pow(i, index));
                    }
                });
                double value = production[TimeHelper.getActualHour()];
                producerDataСЭС.getProducerLoad().put(getAgent().getAID(), value);
                log.info(Color.BLUE + "{} storage is {}" + Color.RESET, "СЭС", producerDataСЭС.getProducerLoad().get(getAgent().getAID()));
            }
        }
    }
}
