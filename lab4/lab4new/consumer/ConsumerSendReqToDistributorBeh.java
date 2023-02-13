package lab.lab4new.consumer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ConsumerSendReqToDistributorBeh extends Behaviour {

    int timer;
    final int maxCost;

    @Override
    public void action() {
        Consumer agent = (Consumer) myAgent;
        ACLMessage request = new ACLMessage(ACLMessage.CFP);
        if (myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE)) != null) {
            switch (myAgent.getLocalName()){
                case "firstConsumer" -> request.addReceiver(new AID("firstDistributor", false));
                case "secondConsumer" -> request.addReceiver(new AID("secondDistributor", false));
                case "thirdConsumer" -> request.addReceiver(new AID("thirdDistributor", false));
            }
            Double firstEnergy = agent.getFirstPower().get(timer);
            Double secondEnergy = agent.getSecondPower().get(timer);
            Double thirdEnergy = agent.getThirdPower().get(timer);
            request.setContent(firstEnergy+";"+secondEnergy+";"+thirdEnergy+";"+maxCost);

            switch (myAgent.getLocalName()) {
                case "firstConsumer" ->
                        log.info("{} | {}ч | отправляет запрос на закупку {} энергии, максимальная цена = {}",
                                myAgent.getLocalName(), timer, firstEnergy, maxCost);
                case "secondConsumer" ->
                        log.info("{} | {}ч | отправляет запрос на закупку {} энергии, максимальная цена = {}",
                                myAgent.getLocalName(), timer, secondEnergy, maxCost);
                case "thirdConsumer" ->
                        log.info("{} | {}ч | отправляет запрос на закупку {} энергии, максимальная цена = {}",
                                myAgent.getLocalName(), timer, thirdEnergy, maxCost);
            }
            if (++timer == 24) timer = 0;
            myAgent.send(request);
        }
    }
    @Override
    public boolean done() {
        return false;
    }
}