package lab.lab4other.Producer.Behaviors;

import lab.lab4other.Producer.Battery;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

// Поведение Агента-Производителя. Имитирует участие в торгах с другими производителями
@Slf4j
public class TopicDialogueBehavior extends Behaviour {

    private int pieceOfPie = 1;
    private AID topic;
    private boolean key = false;
    private double bet = Double.POSITIVE_INFINITY;
    private Battery battery;
    private double limit;
    String kingName;

    TopicDialogueBehavior(AID topic, Battery battery, String kingName){
        this.topic = topic;
        this.battery = battery;
        this.kingName = kingName;
    }


    @Override
    public void onStart() {
        limit = 1000 / (battery.batteryCharge + 1);
    }

    @Override
    public void action() {
        ACLMessage first_msg = getAgent().receive(MessageTemplate.and(MessageTemplate.MatchTopic(topic), MessageTemplate.MatchProtocol("getBet")));
            if (first_msg != null){
                if (first_msg.getSender().getLocalName().equals(kingName)) {
                    double betRival = Double.parseDouble(first_msg.getContent());
                    if (betRival <= bet) {
                        if (0.7 * betRival >= limit) bet = 0.7 * betRival;
                        else if (betRival >= limit) bet = limit;
                        else {
                            key = true;
                            log.info("{} is going out of betting {}!", getAgent().getLocalName(), topic.getLocalName());
                            // возвращаем значение проигравшему в торгах обратно
                            battery.batteryCharge += pieceOfPie;
                        }
                    }
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(topic);
                    msg.setProtocol("getBet");
                    msg.setContent(Double.toString(bet));
                    getAgent().send(msg);
                }
            }
        ACLMessage second_msg = getAgent().receive(MessageTemplate.and(MessageTemplate.MatchTopic(topic), MessageTemplate.MatchProtocol("KeyExit")));
            if (second_msg != null) {
                key = true;
                log.info("I'm {} a winner in a topic {}", getAgent().getLocalName(), topic.getName());
            }
        }

    @Override
    public boolean done() {
        return key;
    }
}
