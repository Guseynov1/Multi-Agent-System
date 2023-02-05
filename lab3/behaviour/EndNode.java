package lab.lab3.behaviour;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Slf4j
public class EndNode extends Behaviour {
    private boolean exit;
    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(MessageTemplate.MatchProtocol("estimate"));
        if (receive != null){
            log.info("Tail (finite) found -> {}", getAgent().getLocalName());
            List<String> way = new ArrayList<>();
            String[] intermediate = receive.getContent().split(";");
            Collections.addAll(way, intermediate[0].replaceAll("[\\[\\]]", "").split(", "));
            way.add(getAgent().getLocalName());
            double actualWayWeight = Double.parseDouble(intermediate[1]);
            getAgent().addBehaviour(new Parallelism(new TheBestChoice(way, actualWayWeight, false), new TerminationOfWork(getAgent(), 5000)));
            exit = true;
        }
    }

    @Override
    public boolean done() {
        return exit;
    }
}
