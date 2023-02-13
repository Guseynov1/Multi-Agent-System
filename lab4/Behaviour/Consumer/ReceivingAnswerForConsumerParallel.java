package lab4.Behaviour.Consumer;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab4.Config.Data;
import lombok.extern.slf4j.Slf4j;
import lab4.Config.Parameters.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ReceivingAnswerForConsumerParallel extends ParallelBehaviour {
    Data consumerData;
    Data onEnd;
    boolean onEndReceiveEnergy, onEndPriceTooLow, onEndHaveNoEnergy, onEndReceiveEnergyAfterDivision;

    public ReceivingAnswerForConsumerParallel(Agent agent, Data consumerData, Data onEnd) {
        super(agent, WHEN_ANY);
        this.consumerData = consumerData;
        this.onEnd = onEnd;

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage reply = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchProtocol("IBoughtEnergy")));
                if (reply != null) {
                    log.info(Color.YELLOW + "{}: I bought {} kW power from {}" + Color.RESET,
                            getAgent().getLocalName(), consumerData.getConsumerLoad(), reply.getContent());
                    onEndReceiveEnergy = true;
                }
                else block();
            }
            @Override
            public boolean done() {
                return onEndReceiveEnergy;
            }
        });

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage reply = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL), MessageTemplate.MatchProtocol("MaxPriceTooLow")));
                if (reply != null) {
                    double lowestPriceInTrade = Double.parseDouble(reply.getContent());
                    consumerData.setConsumerMaxPrice(lowestPriceInTrade + 0.01);
                    log.info("{}: my last price was too low, my new price is {}", getAgent().getLocalName(), lowestPriceInTrade);
                    onEndPriceTooLow = true;
                }
                else block();
            }
            @Override
            public boolean done() {
                return onEndPriceTooLow;
            }
        });

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage reply = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL), MessageTemplate.MatchProtocol("noEnergy")));
                if (reply != null) {
                    log.info(Color.RED + "{}: they don't have any energy for me" + Color.RESET, getAgent().getLocalName());
                    onEndHaveNoEnergy = true;
                }
                else block();
            }
            @Override
            public boolean done() {
                return onEndHaveNoEnergy;
            }
        });

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage reply = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL), MessageTemplate.MatchProtocol("IBoughtEnergyAfterDivision")));
                if (reply != null) {
                    List<String> producers = new ArrayList<>(Arrays.asList(reply.getContent().split(",")));
                    log.info(Color.YELLOW + "{}: I bought {} kw power from {}" + Color.RESET,
                            getAgent().getLocalName(), consumerData.getConsumerLoad() * producers.size() / 2, reply.getContent());
                    onEndReceiveEnergyAfterDivision = true;
                }
                else block();
            }
            @Override
            public boolean done() {
                return onEndReceiveEnergyAfterDivision;
            }
        });
    }

    @Override
    public int onEnd() {
        if (onEndReceiveEnergy) onEnd.setOnEnd(1);
        if (onEndPriceTooLow) onEnd.setOnEnd(2);
        if (onEndHaveNoEnergy) onEnd.setOnEnd(3);
        if (onEndReceiveEnergyAfterDivision) onEnd.setOnEnd(4);
        return super.onEnd();
    }
}
