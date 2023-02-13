package lab.lab4new.distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Distributor;
import lab.lab4new.Auto.Parameters.Color;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.List;

@Slf4j
public class DistributorGetRespFromProducerBehavior extends Behaviour {

    boolean stop = false;
    Integer count = 0;
    int num;
    String bestProducer = "none";
    Double bestCost = 100d;

    List<String> producer;
    Map<String,Double> producerAndCost;
    Double costConsumer, energyConsumerF, energyConsumerS, energyConsumerT;
    public DistributorGetRespFromProducerBehavior(Integer num) {
        this.num = num;
        producer = new ArrayList<>();
        producerAndCost = new HashMap<>();
    }

    @SneakyThrows
    @Override
    public void action() {
        ACLMessage response = myAgent.receive(MessageTemplate
                .or(MessageTemplate.MatchPerformative(ACLMessage.REFUSE), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE)));

        Distributor agent = (Distributor) myAgent;
        energyConsumerF = agent.getEnergyF();
        energyConsumerS = agent.getEnergyS();
        energyConsumerT = agent.getEnergyT();
        costConsumer = agent.getCostConsumer();

            if (response != null) {
                count++;
                switch (response.getPerformative()){
                    case ACLMessage.REFUSE -> {
                        log.info(Color.RED + "Отказ от {}" + Color.RESET, response.getSender().getLocalName());
                    }
                    case ACLMessage.PROPOSE -> {
                        Double costFromProducer = Double.parseDouble(response.getContent().split(";")[3]);
                        if (costFromProducer <= costConsumer) {
                            producer.add(response.getSender().getLocalName());
                            producerAndCost.put(response.getSender().getLocalName(), costFromProducer);
                            log.info("{}: предложение цены {} от {} подходит потребителю", response.getSender().getLocalName(),
                                    String.format("%.3f", costFromProducer), response.getSender().getLocalName());
                            if (bestCost > costFromProducer) {
                                bestCost = costFromProducer;
                                agent.setBestCost(bestCost);
                                agent.setBestProducer(response.getSender().getLocalName());
                            }
                            bestProducer = response.getSender().getLocalName();
                        }
                    }
                }
                if (count == num) stop = true;
            } else block();
    }

    @Override
    public int onEnd() {

        if (producer.size() == 1 && bestCost > costConsumer) {
            myAgent.addBehaviour(new DistributorSendRespToConsumerBehaviour());
        }
        if (producer.size() == 1 && bestCost <= costConsumer) {
            log.info("На рынке один поставщик с подходящей энергией и ценой");
            myAgent.addBehaviour(new DistributorSendOrderToProducerBehaviour());
            myAgent.addBehaviour(new DistributorSendRespToConsumerBehaviour
                    (energyConsumerF, energyConsumerS, energyConsumerT, bestCost));
        }
        if (producer.size() > 1)  {
            myAgent.addBehaviour(new DistributorSendMessageStartChat(producer));
        }
        if (producerAndCost.size() > 1)  {
//            log.info("На рынке один поставщик с подходящей энергией, но высокой ценой");
            myAgent.addBehaviour(new DistributorSendOrderToProducerBehaviour(producerAndCost));
            for (Double value : producerAndCost.values()) {
                myAgent.addBehaviour(new DistributorSendRespToConsumerBehaviour
                        (energyConsumerF, energyConsumerS, energyConsumerT, value));
            }
        }
        return 0;
    }

    @Override
    public boolean done() {
        return stop;
    }
}