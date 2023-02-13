package lab.lab4other.Distributor.Behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

public class BetEndingBehavior extends WakerBehaviour {

    private AID topic;
    public BetEndingBehavior(Agent agent, long timeout, AID topic) {
        super(agent, timeout);
        this.topic = topic;
    }

    @Override
    protected void onWake() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setProtocol("KeyExit");
        msg.setContent("NotNull");
        msg.addReceiver(topic);
        myAgent.send(msg);
        }
    }
