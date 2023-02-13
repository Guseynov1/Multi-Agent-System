package lab4.Behaviour.Producer.FSM;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AfterWin extends OneShotBehaviour {
    String distributersName;
    double neededLoad;
    Data producerData;

    public AfterWin(Agent agent,String distributersName, double neededLoad, Data producerData) {
        super(agent);
        this.distributersName = distributersName;
        this.neededLoad = neededLoad;
        this.producerData = producerData;
    }

    @Override
    public void action() {

        if (producerData.getProducerLoad().get(getAgent().getAID()) >= neededLoad) {
            producerData.getProducerLoad().put(getAgent().getAID(), producerData.getProducerLoad().get(getAgent().getAID()) - neededLoad);
            ACLMessage confirm = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
            confirm.addReceiver(new AID(distributersName, false));
            confirm.setProtocol("ConfirmSelling");
            confirm.setContent(String.valueOf(producerData.getWinnerMSG()));
            getAgent().send(confirm);
            log.info("{} sold energy to {}", getAgent().getLocalName(), distributersName);
        } else log.info("{} already sold all his energy", getAgent().getLocalName());
    }
}
