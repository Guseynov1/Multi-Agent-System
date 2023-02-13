package lab4.Behaviour.Consumer;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import lab4.Config.Data;
import lab4.Helper.TimeHelper;

public class LauncherConsumerFSM extends OneShotBehaviour {
    Data consumerData;
    public LauncherConsumerFSM(Agent agent, Data consumerData) {
        super(agent);
        this.consumerData = consumerData;
    }

    @Override
    public void action() {
        getAgent().addBehaviour(new ConsumerFSM(getAgent(), consumerData));
    }


    public static class ConsumerFSM extends FSMBehaviour {
        Data consumerData;
        Data onEnd = new Data();

        public ConsumerFSM(Agent agent, Data consumerData) {
            super(agent);
            this.consumerData = consumerData;

            registerFirstState(new SendingTaskToAuction(getAgent(), TimeHelper.getDelay() + 2000, consumerData), "SendReq");
            registerState(new ReceivingAnswerForConsumerParallel(getAgent(), consumerData, onEnd), "ReceiveAnswer");
            registerLastState(new LauncherConsumerFSM(getAgent(), consumerData), "Restart");
            registerDefaultTransition("SendReq", "ReceiveAnswer");
            registerDefaultTransition("ReceiveAnswer", "Restart");

        }

    }



}
