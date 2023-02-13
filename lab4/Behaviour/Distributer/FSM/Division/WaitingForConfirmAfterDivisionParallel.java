package lab4.Behaviour.Distributer.FSM.Division;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab4.Config.Data;

public class WaitingForConfirmAfterDivisionParallel extends ParallelBehaviour {
    Data data;
    Data onEnd = new Data();

    public WaitingForConfirmAfterDivisionParallel(Agent agent, long wakeUpTime, Data data) {
        super(agent, WHEN_ANY);
        this.data = data;

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage congratulations = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchProtocol("ConfirmSelling")));
                if (congratulations != null) {
                    data.getBestsPrices().add(congratulations.getContent());
                    onEnd.setOnEnd(1);
                }
                else block();
            }
            @Override
            public boolean done() {
                return false;
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
        return onEnd.getOnEnd();
    }
}
