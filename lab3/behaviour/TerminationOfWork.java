package lab.lab3.behaviour;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class TerminationOfWork extends WakerBehaviour {

    public static boolean lever = false;
    public TerminationOfWork(Agent agent, long time) {
        super(agent, time);
    }
    @Override
    protected void onWake() {
        lever = true;
    }
}
