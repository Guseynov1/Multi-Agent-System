package lab4.Behaviour.Distributer.FSM;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.DistributerConfig;
import lab4.Config.Data.PriceWithName;
import lab4.Helper.XMLHelper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IBoughtEnergy extends OneShotBehaviour {
    PriceWithName bestPrice;

    @Override
    public void action() {
        DistributerConfig cfg = XMLHelper.unMarshalAny(DistributerConfig.class, getAgent().getLocalName() + ".xml");
        ACLMessage minPrice = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        minPrice.setProtocol("IBoughtEnergy");
        minPrice.addReceiver(new AID(cfg.getProducersName(), false));
        minPrice.setContent(bestPrice.getName() + ": price = " + bestPrice.getPrice());
        getAgent().send(minPrice);
    }
}
