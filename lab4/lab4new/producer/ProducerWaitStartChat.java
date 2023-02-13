package lab.lab4new.producer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.ChatRoom.ProducerChat;

public class ProducerWaitStartChat extends Behaviour {

    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
        if (request != null) myAgent.addBehaviour(new ProducerChat(request.getContent()));
        else block();
    }
    @Override
    public boolean done() {
        return false;
    }
}