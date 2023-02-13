package lab4.Behaviour.Producer.FSM;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab4.Config.Data;
import lombok.extern.slf4j.Slf4j;
import lab4.Config.Parameters.Color;


import java.util.Objects;
@Slf4j
public class ReceivingPrices extends ParallelBehaviour {
    double neededLoad;
    Data producerData;
    String distributersName;
    AID topic;

    public ReceivingPrices(Agent agent, double neededLoad, Data producerData, String distributersName, AID topic) {
        super(WHEN_ANY);
        this.neededLoad = neededLoad;
        this.producerData = producerData;
        this.distributersName = distributersName;
        this.topic = topic;

        addSubBehaviour(new Behaviour() {
            @Override
            public void action() {
                ACLMessage priceFromProducer = getAgent().receive(MessageTemplate
                        .and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchProtocol("PriceFromProducer")));
                if (priceFromProducer != null) {
                    if (neededLoad <= producerData.getProducerLoad().get(getAgent().getAID())) {
                        double myPrice = 1000 / producerData.getProducerLoad().get(getAgent().getAID());
                        double minPrice = myPrice / 2;
                        double enemyPrice = Double.parseDouble(priceFromProducer.getContent());
                        String sender = priceFromProducer.getSender().getLocalName();
                        double myNewPrice = 0.9 * enemyPrice;
                        if (enemyPrice < myPrice && !Objects.equals(getAgent().getAID().getLocalName(), sender)) {
                            if (myNewPrice >= minPrice) {
                                ACLMessage newPrice = new ACLMessage(ACLMessage.INFORM);
                                newPrice.setContent(String.valueOf(myNewPrice));
                                newPrice.addReceiver(topic);
                                newPrice.setProtocol("PriceFromProducer");
                                getAgent().send(newPrice);
                                log.info(Color.PURPLE + "{}: forget what i said, this my new price {}, which is much better than {} offers" + Color.RESET,
                                        getAgent().getLocalName(), myNewPrice, priceFromProducer.getSender().getLocalName());
                            } else block();
                        }
                    } else block();
                }
                else block();
            }

            @Override
            public boolean done() {
                return false;
            }
        });

        addSubBehaviour(new WakerBehaviour(getAgent(), 4000) {
            @Override
            protected void onWake() {
                super.onWake();
            }
        });
    }
}
