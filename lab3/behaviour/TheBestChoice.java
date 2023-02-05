package lab.lab3.behaviour;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class TheBestChoice extends Behaviour {
    private List<String> bestWay;
    private double bestWeight;
    private boolean exit;

    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(MessageTemplate.MatchProtocol("estimate"));
        if (receive != null) {
            List<String> way = new ArrayList<>();
            Collections.addAll(way, receive.getContent().split(";")[0].replaceAll("[\\[\\]]", "").split(", "));
            double actualWayWeight = Double.parseDouble(receive.getContent().split(";")[1]);

            if (actualWayWeight < bestWeight) {
                bestWeight = actualWayWeight;
                way.add(getAgent().getLocalName());
                bestWay = way;
                log.info("A new best way: {} with weight -> {}", bestWay, actualWayWeight);
            }
        }

        if (TerminationOfWork.lever) {
            exit = true;
            String finite = "start";
            log.info("The final way: {}", bestWay);
            getAgent().addBehaviour(new MasterAgentBehavior(bestWay, finite, bestWeight));
        }
    }

    @Override
    public boolean done() {
        return exit;
    }
}
