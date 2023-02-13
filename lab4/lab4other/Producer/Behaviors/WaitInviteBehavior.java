package lab.lab4other.Producer.Behaviors;

import lab.lab4other.Producer.Battery;
import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.Behaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

// Поведение Агента-Производителя. Ждет приглашения в топик и подписывает контракт с Дистрибьютором в случае победы в торгах
@Slf4j
public class WaitInviteBehavior extends Behaviour {

    private int pieceOfPie = 1;
    private Battery battery;
    public WaitInviteBehavior(Battery battery) {
        this.battery = battery;
    }

    @Override
    public void action() {
        // блок ожидания сообщения о приглашении в топик
        ACLMessage msg = getAgent().receive(MessageTemplate.MatchProtocol("Registration"));
        if (msg != null) {
            if (battery.batteryCharge > pieceOfPie) {
                // изымаем некоторое количество энергии из доступного резерва на время торгов
                battery.batteryCharge -= pieceOfPie;
                // производитель подписывается на топик
                AID topic = subscribeTopic(msg.getContent());
                getAgent().addBehaviour(new TopicDialogueBehavior(topic, battery, msg.getSender().getLocalName()));
                log.info("{} got inviting from {} to enter a {} topic", getAgent().getLocalName(), msg.getSender().getLocalName(), msg.getContent());
            }
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    /* создаем чат с именем name
     * метод подписывается на топик, созданный дистрибьютором
     * @param name имя агента-производителя, подписывающегося на топик
     * @return
     */
    private AID subscribeTopic(String name) {
        AID jadeTopic = null;
        try {
            TopicManagementHelper topicHelper = (TopicManagementHelper) getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(name);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }
}
