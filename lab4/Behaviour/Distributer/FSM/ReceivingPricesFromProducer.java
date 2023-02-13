package lab4.Behaviour.Distributer.FSM;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab4.Config.Data;

import java.util.List;

public class ReceivingPricesFromProducer extends Behaviour {
    Data priceForDistributerData;
    Data onEnd;
    public ReceivingPricesFromProducer(Agent agent, Data priceForDistributerData, Data onEnd) {
        super(agent);
        this.priceForDistributerData = priceForDistributerData;
        this.onEnd = onEnd;
    }

    @Override
    public void action() {
        ACLMessage priceFromProducer = getAgent().receive(MessageTemplate
                .and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchProtocol("PriceFromProducer")));
        if (priceFromProducer != null) {
            double hisPrice = Double.parseDouble(priceFromProducer.getContent());
            List<Data.PriceWithName> prices = priceForDistributerData.getPricesWithNames();
            prices.add(new Data.PriceWithName(hisPrice, priceFromProducer.getSender().getLocalName()));
            priceForDistributerData.setPricesWithNames(prices);
            onEnd.setOnEnd(1);
        }
        else block();
    }

    @Override
    public boolean done() { return false; }
}
