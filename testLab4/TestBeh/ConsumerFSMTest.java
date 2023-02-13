package testLab4.TestBeh;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Behaviour.Consumer.ReceivingAnswerForConsumerParallel;
import lab4.Config.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerFSMTest extends FSMBehaviour {
    Data consumerData;
    Data onEnd = new Data();
    double load, maxPrice;


    public ConsumerFSMTest(Data consumerData, double load, double maxPrice) {
        this.consumerData = consumerData;
        this.load = load;
        this.maxPrice = maxPrice;

        registerFirstState(new WakerBehaviour(getAgent(), 21000) {
            @Override
            protected void onWake() {
                log.info("{}: My current load is {}", getAgent().getLocalName(), load);
                ACLMessage needs = new ACLMessage(ACLMessage.REQUEST);
                needs.setContent(load + "," + maxPrice);
                needs.setProtocol("Task");
                needs.addReceiver(new AID("firstDistributer", false));
                getAgent().send(needs);
                consumerData.setConsumerLoad(load);
            }
        }, "SendReq");
        registerLastState(new ReceivingAnswerForConsumerParallel(getAgent(), consumerData, onEnd), "ReceivingAnswer");
        registerDefaultTransition("SendReq", "ReceivingAnswer");
    }

    @Override
    public int onEnd() {
        return onEnd.getOnEnd();
    }

}
