package lab.lab4other.Consumer.Behaviors;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс поведения Агента-Потребителя
 * На входе отправляет сообщение о количестве необходимой ему энергии для покрытия графика нагрузки на следующий час.
 * Принимает сообщения от Агента-Дистрибьютора о количестве купленной энергии.
 * Если дистрибьютор успевает купить нужное количество энергии до истечения часа, то поведение извещает об этом.
 * Параллельно принимает сообщения от WakerBehavior.
 * Если при принятии сообщения об окончании поведения необходимое количество ЭЭ не доставлено, то план на час не выполнен и агент извещает об этом.
 */
@Slf4j
public class SendRequestAndWaitBehavior extends Behaviour {

    static boolean keyExit;
    private double energyLess;
    private boolean lever = false;

    /**
     * @param energyLess количество необходимой потребителю энергии на следующий час
     */
    SendRequestAndWaitBehavior(double energyLess) {
        this.energyLess = energyLess;
    }

    @Override
    public void onStart() {
        // сообщение-инициация о потребности в ЭЭ
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setContent(String.valueOf(energyLess));
        msg.setProtocol("giveMeElectricity");
        msg.addReceiver(stack(getAgent().getLocalName()));
        getAgent().send(msg);
        keyExit = true;
    }

    @Override
    public void action() {
        // получает сообщение, содержащее купленную для потребителя энергию
        ACLMessage receive = getAgent().receive(MessageTemplate.MatchProtocol("get"));
        if (receive != null) {
            // из количества необходимой энергии вычитается полученная
            energyLess = energyLess - Double.parseDouble(receive.getContent());
            if (energyLess <= 0) {
                log.info("Got all quantity");
                lever = true;
            } else log.info("Потребителю {} осталось покрыть {}", getAgent().getLocalName(), energyLess);
        }
        // при пробуждении Waker с ключом приходит оповещение о конце ожидания
        if (!keyExit) {
            System.err.println("Distributor is failed! Not Enough energy!");
            lever = true;
        }
    }

    /**
     * @param agentName задается имя агента, выполняющего операцию
     * @return имя получателя сообщения
     */
    AID stack(String agentName){
        return switch (agentName) {
            case "AgentConsumer1" -> new AID("AgentDistributor1", false);
            case "AgentConsumer2" -> new AID("AgentDistributor2", false);
            case "AgentConsumer3" -> new AID("AgentDistributor3", false);
            default -> null;
        };
    }

    @Override
    public boolean done() {
        return lever;
    }
}
