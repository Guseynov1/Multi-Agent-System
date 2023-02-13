package lab4.Behaviour.Distributer.FSM.Division;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.DistributerConfig;
import lab4.Helper.XMLHelper;
import lombok.extern.slf4j.Slf4j;
import lab4.Config.Parameters.Color;


@Slf4j
public class TheyDontHaveEnergy extends OneShotBehaviour {

    @Override
    public void action() {
        DistributerConfig cfg = XMLHelper.unMarshalAny(DistributerConfig.class, getAgent().getLocalName() + ".xml");
        ACLMessage minPrice = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
        minPrice.addReceiver(new AID(cfg.getProducersName(), false));
        minPrice.setProtocol("noEnergy");
        getAgent().send(minPrice);
        log.info(Color.RED + "{} didn't buy any energy for {} because seller don't have enough energy" + Color.RESET,
                getAgent().getLocalName(), cfg.getProducersName());
    }
}
