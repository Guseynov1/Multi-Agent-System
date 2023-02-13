package lab.lab4new.ChatRoom;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Distributor;
import lab.lab4new.distributor.DistributorSendOrderToProducerBehaviour;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class DistributorChat extends Behaviour {

    boolean stop = false;
    List<String> producers;
    Distributor agent;
    int countOut;

    public DistributorChat(List<String> producers) {
        this.producers = producers;
        countOut = producers.size();
    }

    @SneakyThrows
    @Override
    public void onStart() {
        agent = (Distributor) myAgent;
        log.info("{} я в чате. Начальная цена: {}", myAgent.getLocalName(), agent.getBestCost());
        Thread.sleep(100);
        ACLMessage startAuction = new ACLMessage(ACLMessage.INFORM_REF);
        // Не лучшему производителю отправляю письмо с начальной ценой для аукциона
        producers.forEach(producer -> {
            if (!producer.equals(agent.getBestProducer())) startAuction.addReceiver(new AID(producer, false));
        });
        startAuction.setContent(myAgent.getLocalName()+";"+agent.getBestCost());
        myAgent.send(startAuction);
    }

    @SneakyThrows
    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF));
        if (request != null) {
            System.out.println(request.getContent());
            if (request.getContent().startsWith("Цена")) countOut--;
            else {
                agent.setBestProducer(request.getContent().split(";")[0]);
                agent.setBestCost(Double.parseDouble(request.getContent().split(";")[3]));
            }
            if (countOut == 1) {
                ACLMessage stopAuction = new ACLMessage(ACLMessage.INFORM_REF);
                stopAuction.addReceiver(new AID(agent.getBestProducer(), false));
                stopAuction.setContent("stop;" + agent.getEnergyF() + agent.getEnergyS() + agent.getEnergyT());
                myAgent.addBehaviour(new DistributorSendOrderToProducerBehaviour());
                myAgent.send(stopAuction);
                Thread.sleep(100);
                stop = true;
            }
        } else block();
    }

    @Override
    public boolean done() {
        return stop;
    }
}