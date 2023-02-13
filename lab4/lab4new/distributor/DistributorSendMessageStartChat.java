package lab.lab4new.distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab.lab4new.ChatRoom.DistributorChat;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@AllArgsConstructor
@Slf4j
public class DistributorSendMessageStartChat extends OneShotBehaviour {
    List<String> producers;

    @Override
    public void action() {
        log.info("Есть несколько поставщиков, можно начинать торги");
        ACLMessage request = new ACLMessage(ACLMessage.CONFIRM);
        String collectProd = String.join(", ", producers);
        String collectAll = collectProd + ", " + myAgent.getLocalName();
        log.info("Список участников чата: {}", collectAll);
        request.setContent(collectAll);
        producers.forEach(producer -> request.addReceiver(new AID(producer, false)));
        myAgent.addBehaviour(new DistributorChat(producers));
        myAgent.send(request);
    }
}