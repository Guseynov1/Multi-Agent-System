package lab.lab4new.distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab.lab4new.Agents.Distributor;

import java.util.Map;
import java.util.Set;

public class DistributorSendOrderToProducerBehaviour extends OneShotBehaviour {
    Map<String,Double> producerAndCost;
    int count;
    public DistributorSendOrderToProducerBehaviour() {
        this.count = 1;
    }
    public DistributorSendOrderToProducerBehaviour(Map<String,Double> producerAndCost) {
        this.producerAndCost = producerAndCost;
        this.count = producerAndCost.size();
    }

    @Override
    public void action() {
        Distributor agent = (Distributor) myAgent;
        if (count == 1) {
            ACLMessage sendOrder = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
            sendOrder.addReceiver(new AID(agent.getBestProducer(), false));
            sendOrder.setContent("" + agent.getEnergyF());
            myAgent.send(sendOrder);
        } else {
            Set<String> producers = producerAndCost.keySet();
            ACLMessage sendOrder = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
            producers.forEach(producer -> sendOrder.addReceiver(new AID(producer, false)));
            sendOrder.setContent("" + agent.getEnergyF());
            myAgent.send(sendOrder);
        }
    }
}