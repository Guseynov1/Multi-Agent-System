package lab4.Behaviour.Producer;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab4.Config.Data;
import lab4.Helper.DfHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ReceiveTask extends Behaviour {
    Data producerData;

    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(MessageTemplate
                .and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchProtocol("topicName")));
        if (receive != null)
            getAgent().addBehaviour(new Behaviour() { // sendPriceForTopic
                @Override
                public void action() {
                    AID topic = DfHelper.createTopic(getAgent(), receive.getContent());
                    ACLMessage receive = getAgent().receive(MessageTemplate
                            .and(MessageTemplate.MatchPerformative(ACLMessage.CFP), MessageTemplate.MatchProtocol("Task")));
                    if (receive != null) {
                        double neededLoad = Double.parseDouble(receive.getContent());
                        getAgent().addBehaviour(new ProducerFSM(getAgent(), receive.getSender().getLocalName(), neededLoad, producerData, topic));
                        log.info("{}: {}", getAgent().getLocalName(), receive.getContent());
                    }
                    else block();
                }

                @Override
                public boolean done() { return false; }
            });
        else block();
    }

    @Override
    public boolean done() {
        return false;
    }
}
