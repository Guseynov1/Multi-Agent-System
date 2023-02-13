package lab4.Behaviour.Distributer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab4.Config.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiveTaskToAuction extends Behaviour {

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate
                .and(MessageTemplate.MatchProtocol("Task"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST)));
        if(msg != null){
            Data data = new Data();
            String[] parseData = msg.getContent().split(",");
            data.setDistributerLoad(Double.parseDouble(parseData[0]));
            data.setDistributerMaxPrice(Double.parseDouble((parseData[1])));
            getAgent().addBehaviour(new DistributerFSM(getAgent(), data));
            log.info("I get the load: {} with the maximum price {}", data.getDistributerLoad(), data.getDistributerMaxPrice());
        }
        else block();
    }

    @Override
    public boolean done() {
        return false;
    }
}
