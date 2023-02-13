package lab4.Behaviour.Distributer.FSM;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingForConfirmParallel extends ParallelBehaviour {
    private boolean confirm;

    public WaitingForConfirmParallel(Agent agent, long wakeUpTime) {
        super(agent, WHEN_ANY);

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage congratulations = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchProtocol("ConfirmSelling")));
                if (congratulations != null) confirm = true;
                else block();
            }
            @Override
            public boolean done() {
                return confirm;
            }
        });

        addSubBehaviour(new WakerBehaviour(getAgent(), wakeUpTime) {
            @Override
            protected void onWake() {
                super.onWake();
            }
        });
    }


    @Override
    public int onEnd() {
        if (confirm) return 1;
        else return 2;
    }
}
