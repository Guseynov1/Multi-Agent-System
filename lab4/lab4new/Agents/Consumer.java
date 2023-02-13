package lab.lab4new.Agents;

import jade.core.Agent;
import lab.lab4new.Auto.Parameters;
import lab.lab4new.Auto.ConfigReader;
import lab.lab4new.consumer.ConsumerSendReqToDistributorBeh;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static lab.lab4new.Agents.Smith.registration;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class Consumer extends Agent {
    private List<Double> firstPower, secondPower, thirdPower;

    @Override
    @SneakyThrows
    protected void setup() {
        firstPower = getParameters("firstConsumer");
        secondPower = getParameters("secondConsumer");
        thirdPower = getParameters("thirdConsumer");

        log.info("{} агент запущен", this.getLocalName());
        this.addBehaviour(new ConsumerSendReqToDistributorBeh(5));
        // Поиск агентов производителей по сервису Consumer, который они предоставляют
        registration("Consumer", getAID(), getLocalName(), this);
    }

    public List<Double> getParameters(String name) {
        Parameters parameters = ConfigReader.getParameters(name);
        return parameters.getPower();
    }


}