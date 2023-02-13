package lab4.Behaviour.Distributer.FSM.Division;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DivisionContract extends OneShotBehaviour {
    Data data;
    public DivisionContract(Agent agent, Data data) {
        super(agent);
        this.data = data;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        msg.setProtocol("Task");
        msg.addReceiver(data.getTopic());
        msg.setContent(String.valueOf(data.getDistributerLoad() / 2));
        getAgent().send(msg);
        log.info("new task load {}", data.getDistributerLoad() / 2);
    }
}
