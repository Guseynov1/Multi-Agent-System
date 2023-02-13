package lab.lab4other.Consumer.Behaviors;

import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;

public class TimeToEatBehavior extends WakerBehaviour {
   public TimeToEatBehavior(Agent agent, long timeout) {
        super(agent, timeout);
    }
    @Override
    protected void onWake() {
        SendRequestAndWaitBehavior.keyExit = false;
        getAgent().addBehaviour(new DiscoverLoadBehavior());
    }
}
