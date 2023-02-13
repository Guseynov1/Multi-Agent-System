package lab.lab4new.producer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Producer;
import lab.lab4new.Auto.Parameters.Color;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerGetOrderFromDistributorBeh extends Behaviour {

    Producer agent;
    @Override
    public void onStart() {
        agent = (Producer) myAgent;
    }

    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL));
        if (request != null) {
            double energySold = Double.parseDouble(request.getContent());
            agent.setEnergy(agent.getEnergy() - energySold);
            String remains = String.format("%.2f", agent.getEnergy());
            log.info(Color.CYAN + "{} продал {} кВт для {}, осталось {}" + Color.RESET,
                    myAgent.getLocalName(), energySold, request.getSender().getLocalName(), remains);
        } else block();
    }

    @Override
    public boolean done() {
        return false;
    }
}