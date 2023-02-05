package lab.lab2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class ProvideParametersBehaviour extends WakerBehaviour {
    List<AID> receivers;
    double x, delta;

    public ProvideParametersBehaviour(Agent agent, long time, List<AID> receivers, double x, double delta) {
        super(agent, time);
        this.receivers = receivers;
        this.x = x;
        this.delta = delta;
    }

    @Override
    public void onWake() {
        receivers.forEach(receiver -> {
            ACLMessage transInit = new ACLMessage(ACLMessage.REQUEST);
            transInit.setProtocol("CalcFunction");
            transInit.setContent(x + ", " + delta);
            transInit.addReceiver(receiver);
            myAgent.send(transInit);
        });
    }
}