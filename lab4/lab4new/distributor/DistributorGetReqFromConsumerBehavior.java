package lab.lab4new.distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Distributor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributorGetReqFromConsumerBehavior extends Behaviour {

    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CFP));
        Distributor agent = (Distributor) myAgent;
        if (request != null) {
            double energyValueF = Double.parseDouble(request.getContent().split(";")[0]);
            double energyValueS = Double.parseDouble(request.getContent().split(";")[1]);
            double energyValueT = Double.parseDouble(request.getContent().split(";")[2]);
            double maxCost = Double.parseDouble(request.getContent().split(";")[3]);
            agent.setEnergyF(energyValueF);
            agent.setEnergyS(energyValueS);
            agent.setEnergyT(energyValueT);
            agent.setCostConsumer(maxCost);

            switch (myAgent.getLocalName()) {
                case "firstDistributor" ->
                        log.info("{} получил запрос от {} на покупку {} кВт за {} рублей",
                                myAgent.getLocalName(), request.getSender().getLocalName(), energyValueF, maxCost);
                case "secondDistributor" ->
                        log.info("{} получил запрос от {} на покупку {} кВт за {} рублей",
                                myAgent.getLocalName(), request.getSender().getLocalName(), energyValueS, maxCost);
                case "thirdDistributor" ->
                        log.info("{} получил запрос от {} на покупку {} кВт за {} рублей",
                                myAgent.getLocalName(), request.getSender().getLocalName(), energyValueT, maxCost);
            }
            myAgent.addBehaviour(new DistributorSendReqToProducerBehavior());
        } else block();
    }

    @Override
    public boolean done() {
        return false;
    }
}