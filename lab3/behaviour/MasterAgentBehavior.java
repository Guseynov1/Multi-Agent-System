package lab.lab3.behaviour;

import lab.lab3.container.xmlHandler;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@AllArgsConstructor
@Slf4j
public class MasterAgentBehavior extends OneShotBehaviour {

    private List<String> way;
    private String finite;
    private double weight;

    @Override
    public void action() {
        List<String> actualWay = new ArrayList<>(way);
        actualWay.add(getAgent().getLocalName());
        String[][] lists = xmlHandler.unMarshalAny(getAgent().getLocalName() + ".xml");
        for (String[] list : lists) {
            if (filter(way, list[0], finite)) continue;
            if (goHome(finite, way)) break;
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            double actualWeight = weight + Double.parseDouble(list[1]);
            msg.addReceiver(new AID(list[0], false));
            msg.setContent(actualWay + ";" + actualWeight + ";" + finite);
            msg.setProtocol("neighbor");
            getAgent().send(msg);
        }
    }

    private boolean filter(List<String> nodeList, String agentNeighbor, String finite) {
        AtomicBoolean lever = new AtomicBoolean(false);
        if (nodeList != null) {
            nodeList.forEach(listz -> {
                if (listz.equals(agentNeighbor) & !finite.equals("start")) lever.set(true);
            });
        }
        return lever.get();
    }

    private boolean goHome(@NotNull String MSG, List<String> way) {
        boolean lever = false;
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        if (MSG.equals("start")) {
            int sender = way.indexOf(getAgent().getLocalName());
            String receiver = way.get(sender - 1);
            msg.addReceiver(new AID(receiver, false));
            msg.setContent(way + ";" + weight + ";" + MSG);
            msg.setProtocol("neighbor");
            getAgent().send(msg);
            log.info("We're going back -> {} sends message to {} with good news!", getAgent().getLocalName(), receiver);
            lever = true;
        }
        return lever;
    }
}
