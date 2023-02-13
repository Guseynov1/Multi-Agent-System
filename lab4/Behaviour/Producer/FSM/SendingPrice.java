package lab4.Behaviour.Producer.FSM;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendingPrice extends OneShotBehaviour {

    String distributersName;
    double neededLoad;
    Data producerData;
    AID topic;
    int onEnd;
    public SendingPrice(Agent agent, String distributersName, double neededLoad, Data producerData, AID topic) {
        super(agent);
        this.distributersName = distributersName;
        this.neededLoad = neededLoad;
        this.producerData = producerData;
        this.topic = topic;
    }

    @Override
    public void action() {
        if (neededLoad < producerData.getProducerLoad().get(getAgent().getAID())) {
            double myPrice = 500 / producerData.getProducerLoad().get(getAgent().getAID());
            ACLMessage price = new ACLMessage(ACLMessage.INFORM);
            price.setContent(String.valueOf(myPrice));
            price.addReceiver(topic);
            price.setProtocol("PriceFromProducer");
            getAgent().send(price);
            log.info("{} sent his price {}", getAgent().getLocalName(), myPrice);
            onEnd = 1;
        } else onEnd = 2;
    }

    @Override
    public int onEnd() { return onEnd; }
}
