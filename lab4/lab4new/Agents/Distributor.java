package lab.lab4new.Agents;

import jade.core.Agent;
import lab.lab4new.distributor.DistributorGetReqFromConsumerBehavior;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class Distributor extends Agent {

    private Double energyF, energyS, energyT, costConsumer, bestCost = 0d;
    private String bestProducer;

    @Override
    protected void setup() {
        log.info("{} запущен", this.getLocalName());
        this.addBehaviour(new DistributorGetReqFromConsumerBehavior());
    }
}