package lab4.Behaviour.Distributer.FSM;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.DistributerConfig;
import lab4.Config.Data;
import lab4.Config.Data.PriceWithName;
import lab4.Helper.XMLHelper;

public class ChoosingBestPrice extends OneShotBehaviour {
    Data data;
    Data priceForDistributerData;
    PriceWithName bestPrice;

    public ChoosingBestPrice(Agent agent, Data data, Data priceForDistributerData, PriceWithName bestPrice) {
        super(agent);
        this.data = data;
        this.priceForDistributerData = priceForDistributerData;
        this.bestPrice = bestPrice;
    }

    @Override
    public void action() {
        bestPrice.setPrice(priceForDistributerData.getPricesWithNames().get(priceForDistributerData.getPricesWithNames().size() - 1).getPrice());
        bestPrice.setName(priceForDistributerData.getPricesWithNames().get(priceForDistributerData.getPricesWithNames().size() - 1).getName());
        if (bestPrice.getPrice() < data.getDistributerMaxPrice()) {
            ACLMessage win = new ACLMessage(ACLMessage.PROPOSE);
            win.addReceiver(new AID(bestPrice.getName(), false));
            win.setProtocol("Winner");
            getAgent().send(win);
        } else {
            DistributerConfig cfg = XMLHelper.unMarshalAny(DistributerConfig.class, getAgent().getLocalName() + ".xml");
            ACLMessage minPrice = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
            minPrice.setContent(String.valueOf(bestPrice.getPrice()));
            minPrice.addReceiver(new AID(cfg.getProducersName(), false));
            minPrice.setProtocol("MaxPriceTooLow");
            getAgent().send(minPrice);
        }
    }
}
