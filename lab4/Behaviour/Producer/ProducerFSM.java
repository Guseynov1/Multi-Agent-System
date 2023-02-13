package lab4.Behaviour.Producer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import lab4.Behaviour.Producer.FSM.*;
import lab4.Config.Data;

public class ProducerFSM extends FSMBehaviour {
    String distributersName;
    double neededLoad;
    Data producerData;
    AID topic;

    public ProducerFSM(Agent agent, String distributersName, double neededLoad, Data producerData, AID topic) {
        super(agent);
        this.distributersName = distributersName;
        this.neededLoad = neededLoad;
        this.producerData = producerData;
        this.topic = topic;

        registerFirstState(new SendingPrice(getAgent(), distributersName, neededLoad, producerData, topic), "SendPrice");
        registerState(new ReceivingPrices(getAgent(), neededLoad, producerData, distributersName, topic), "ReceivePrice");
        registerState(new WaitingForDecision(getAgent(),6000, distributersName, producerData, neededLoad), "WaitForDecision");
        registerLastState(new AfterWin(getAgent(), distributersName, neededLoad, producerData), "Winner");
        registerLastState(new StateOfEnergy(), "noEnergy");
        registerLastState(new StateOfEnergy.Bought(), "BoughtEnergy");

        registerDefaultTransition("SendPrice", "ReceivePrice");
        registerDefaultTransition("ReceivePrice", "WaitForDecision");
        registerTransition("WaitForDecision", "Winner", 1);
        registerTransition("WaitForDecision", "noEnergy",2);
        registerTransition("WaitForDecision", "BoughtEnergy",3);
    }


}
