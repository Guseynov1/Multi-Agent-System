package lab4.Behaviour.Distributer.FSM.Division;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.DistributerConfig;
import lab4.Config.Data;
import lab4.Helper.XMLHelper;

public class MinPriceTooLargeAfterDivision extends OneShotBehaviour {
    Data priceForDistributerData;
    public MinPriceTooLargeAfterDivision(Agent agent, Data priceForDistributerData) {
        super(agent);
        this.priceForDistributerData = priceForDistributerData;
    }

    @Override
    public void action() {
        DistributerConfig cfg = XMLHelper.unMarshalAny(DistributerConfig.class, getAgent().getLocalName() + ".xml");
        ACLMessage minPrice = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
        minPrice.addReceiver(new AID(cfg.getProducersName(), false));
        minPrice.setProtocol("MaxPriceTooLow");
        minPrice.setContent(String.valueOf(priceForDistributerData.getMinPrice()));
        getAgent().send(minPrice);
    }
}
