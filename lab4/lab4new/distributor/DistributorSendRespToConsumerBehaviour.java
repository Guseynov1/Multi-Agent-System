package lab.lab4new.distributor;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lab.lab4new.Auto.Parameters.Color;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class DistributorSendRespToConsumerBehaviour extends OneShotBehaviour {

    Double energyF, energyS, energyT, cost;

    @Override
    public void action() {
        ACLMessage request = new ACLMessage(ACLMessage.INFORM);
        switch (myAgent.getLocalName()) {
            case "firstDistributor" -> request.addReceiver(new AID("firstConsumer", false));
            case "secondDistributor" -> request.addReceiver(new AID("secondConsumer", false));
            case "thirdDistributor" -> request.addReceiver(new AID("thirdConsumer", false));
        }
        if (cost != null) {
            switch (myAgent.getLocalName()) {
                case "firstDistributor" -> log.info(Color.BLUE + "{}: отправляю {}: {} энергию по {} руб/кВт*ч" + Color.RESET,
                        myAgent.getLocalName(), "firstConsumer", energyF, String.format("%.3f", cost));
                case "secondDistributor" -> log.info(Color.BLUE + "{}: отправляю {}: {} энергию по {} руб/кВт*ч" + Color.RESET,
                        myAgent.getLocalName(), "secondConsumer", energyS, String.format("%.3f", cost));
                case "thirdDistributor" -> log.info(Color.BLUE + "{}: отправляю {}: {} энергию по {} руб/кВт*ч" + Color.RESET,
                        myAgent.getLocalName(), "thirdConsumer", energyT, String.format("%.3f", cost));
            }
            request.setContent(energyF + ";" + energyS + ";" + energyT + ";" + cost);
        } else {
            log.info(Color.RED + "{}: не удалось купить ЭЭ" + Color.RESET, myAgent.getLocalName());
            request.setContent("null;null");
        }
        myAgent.send(request);
    }
}