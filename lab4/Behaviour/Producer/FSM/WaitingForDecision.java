package lab4.Behaviour.Producer.FSM;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab4.Config.Data;

public class WaitingForDecision extends ParallelBehaviour {
    double neededLoad;
    String distributersName;
    Data producerData;
    private boolean wonWaitingForWin, wonWaitingForWinAfterDiv;

    public WaitingForDecision(Agent agent,long wakeUpTime, String distributersName, Data producerData, double neededLoad) {
        super(agent, WHEN_ANY);
        this.distributersName = distributersName;
        this.producerData = producerData;
        this.neededLoad = neededLoad;

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage winner = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchProtocol("Winner")));
                if (winner != null) {
                    producerData.setWinnerMSG(winner.getContent());
                    if (producerData.getProducerLoad().get(getAgent().getAID()) >= neededLoad) {
                        producerData.getProducerLoad().put(getAgent().getAID(), producerData.getProducerLoad().get(getAgent().getAID()) - neededLoad);
                        ACLMessage confirm = winner.createReply();
                        confirm.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        confirm.setProtocol("ConfirmSelling");
                        confirm.setContent(String.valueOf(producerData.getWinnerMSG()));
                        getAgent().send(confirm);
                        wonWaitingForWin = true;
                    }
                }
                else block();
            }
            @Override
            public boolean done() {
                return wonWaitingForWin;
            }
        });

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage winner = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchProtocol("WinnerAfterDiv")));
                if (winner != null) {
                    if (winner.getSender().getLocalName().equals(distributersName)) {
                        producerData.setWinnerMSG(winner.getContent());
                        wonWaitingForWinAfterDiv = true;
                    }
                }
                else block();
            }

            @Override
            public boolean done() {
                return wonWaitingForWinAfterDiv;
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
        if (wonWaitingForWinAfterDiv) return 1;
        if (wonWaitingForWin) return 3;
        else return 2;
    }
}
