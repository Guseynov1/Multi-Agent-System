package lab.lab4other.Distributor.Behaviors;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Поведение Агента-Распределителя
 * Ожидает запроса на покупку ЭЭ от Агента-Потребителя, создает FSM-поведение, в котором происходят последующие торги
 */

public class WaitConsumer extends Behaviour {

    @Override
    public void action() {
        ACLMessage receive = getAgent().receive(MessageTemplate.MatchProtocol("giveMeElectricity"));
        if (receive != null) {
            // получаем сообщение от потребителей о нужде в ЭЭ
            double quantity = Double.parseDouble(receive.getContent());
            // запуск ФСМ
            getAgent().addBehaviour(new TraderBehaviorFSM(quantity));
        }
    }
    @Override
    public boolean done() {
        return false;
    }
}
