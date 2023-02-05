package lab.lab2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
public class Transfer extends WakerBehaviour {
    double x, delta;
    private final List<String> agents = Arrays.asList("FirstAgent", "SecondAgent", "ThirdAgent");
    private final Random random = new Random();
    int initiator = random.nextInt(agents.size());

    public Transfer(Agent agent, long time, double x, double delta) {
        super(agent, time);
        this.x = x;
        this.delta = delta;
    }

    @Override
    protected void onWake() {
        while (agents.get(initiator).equals(myAgent.getLocalName())){
            initiator = random.nextInt(agents.size());
        }
        ACLMessage initTrans = new ACLMessage(ACLMessage.INFORM);
        initTrans.setProtocol("Initiator");
        initTrans.setContent(x + ", " + delta);
        initTrans.addReceiver(new AID(agents.get(initiator), false));
        myAgent.send(initTrans);
    }
}