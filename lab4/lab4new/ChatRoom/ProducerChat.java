package lab.lab4new.ChatRoom;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Producer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
@Slf4j
public class ProducerChat extends Behaviour {

    boolean stop = false;
    Double currentCost = 5d;
    List<String> chatUser;
    String testChatUser;
    Producer agent;
    Double minCost;

    public ProducerChat(String chatUser) {
        String[] splitChatUser = chatUser.split(";");
        this.chatUser = Arrays.asList(splitChatUser);
        this.testChatUser = chatUser;
    }

    @Override
    public void onStart() {
        log.info("{} в чате", myAgent.getLocalName());
        agent = (Producer) myAgent;
        minCost = agent.getMinCost();
    }

    @SneakyThrows
    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM_REF));
        if (request != null) {
            String[] mess = request.getContent().split(";");
            Thread.sleep(30);
            if (mess[0].equals("stop")) stop = true;
            else {
                if (!mess[0].equals(myAgent.getLocalName()) && !mess[0].startsWith("Цена")) {
                    ACLMessage auction = new ACLMessage(ACLMessage.INFORM_REF);
                    log.info("{}: вижу цену {}", myAgent.getLocalName(), request.getSender().getLocalName());

                    double cost = Double.parseDouble(mess[1]) - 0.5;
                    if (cost < minCost) {
                        auction.setContent("Цена " + cost + " не подходит " + myAgent.getLocalName()+";");
                        log.info("{}: снизить до {} не могу, выхожу с торгов: ", myAgent.getLocalName(), String.format("%.3f", cost));
                        stop = true;
                    } else {
                        auction.setContent(myAgent.getLocalName() + ";" + cost);
                        log.info("{}: снижаю цену до {}", myAgent.getLocalName(), String.format("%.3f", cost));
                        currentCost = cost;
                    }
                    chatUser.forEach(chatUsers -> auction.addReceiver(new AID(chatUsers, false)));
                    myAgent.send(auction);
                }
            }
        } else block();
    }

    @Override
    public int onEnd() {
        log.info("{}: выхожу из чата", myAgent.getLocalName());
        return 0;
    }

    @Override
    public boolean done() {
        return stop;
    }
}