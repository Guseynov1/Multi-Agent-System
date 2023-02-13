package lab4.Behaviour.Distributer.FSM.Division;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.DistributerConfig;
import lab4.Config.Data;
import lab4.Helper.XMLHelper;

public class SendingReport extends OneShotBehaviour {
    Data data;
    public SendingReport(Agent agent, Data data) {
        super(agent);
        this.data = data;
    }

    @Override
    public void action() {
        DistributerConfig cfg = XMLHelper.unMarshalAny(DistributerConfig.class, getAgent().getLocalName() + ".xml");
        ACLMessage minPrice = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        minPrice.addReceiver(new AID(cfg.getProducersName(), false));
        minPrice.setProtocol("IBoughtEnergyAfterDivision");
        minPrice.setContent(String.valueOf(data.getBestsPrices()));
        getAgent().send(minPrice);
    }
}
