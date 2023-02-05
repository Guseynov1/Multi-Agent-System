package lab.lab3.behaviour;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ReceivingAndProcessingMSG extends Behaviour {
    private boolean exit;
    @Override
    public void action() {
        List<String> way = new ArrayList<>();
        ACLMessage receive = getAgent().receive(MessageTemplate.MatchProtocol("neighbor"));
        if (receive != null) {
            Collections.addAll(way, receive.getContent().split(";")[0].replaceAll("[\\[\\]]", "").split(", "));
            double actualWayWeight = Double.parseDouble(receive.getContent().split(";")[1]);
            String finite = receive.getContent().split(";")[2];
            if (finite.equals(getAgent().getLocalName())) {
                log.info("Finite got from {} -> way: {}, weight: {}", receive.getSender().getLocalName(), way, actualWayWeight);
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setProtocol("estimate");
                msg.setContent(receive.getContent());
                msg.addReceiver(getAgent().getAID());
                getAgent().send(msg);
            }
            else if (getAgent().getLocalName().equals(way.get(0)) & finite.equals("start")) {
                log.info("Algorithm has returned to start:\nBest way: {}\nBest weight: {}", way, actualWayWeight);
                exit = true;
            } else getAgent().addBehaviour(new MasterAgentBehavior(way, finite, actualWayWeight));
        }
    }

    @Override
    public boolean done() {
        return exit;
    }
}



