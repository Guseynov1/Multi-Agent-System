package lab.lab4new.distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab.lab4new.Agents.Distributor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static lab.lab4new.Agents.Smith.getYellowPages;
@NoArgsConstructor
@Slf4j
public class DistributorSendReqToProducerBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        Distributor agent = (Distributor) myAgent;
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setContent(agent.getEnergyF() + ";" + agent.getEnergyS() + ";" + agent.getEnergyT() + ";" + agent.getCostConsumer());
        log.info("{} отправляю запрос производителям", myAgent.getLocalName());
        String[] producers = getYellowPages(myAgent, "Producer");
        Arrays.stream(producers).forEach(producer -> request.addReceiver(new AID(producer, false)));
        myAgent.addBehaviour(new DistributorGetRespFromProducerBehavior(producers.length));
        myAgent.send(request);
    }
}