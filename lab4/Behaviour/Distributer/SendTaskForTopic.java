package lab4.Behaviour.Distributer;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.Data;

public class SendTaskForTopic extends WakerBehaviour {
    Data data;
    public SendTaskForTopic(Agent agent, long timeout, Data data) {
        super(agent, timeout);
        this.data = data;
    }

    @Override
    protected void onWake() {
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        msg.setContent(String.valueOf(data.getDistributerLoad()));
        msg.setProtocol("Task");
        msg.addReceiver(data.getTopic());
        getAgent().send(msg);
    }
}
