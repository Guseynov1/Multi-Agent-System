package lab4.Agent;

import jade.core.Agent;
import lab4.Behaviour.Consumer.LauncherConsumerFSM;
import lab4.Config.Data;


public class Consumer extends Agent {
    Data consumerData = new Data();

    @Override
    protected void setup() {
        consumerData.setConsumerMaxPrice(300);
        addBehaviour(new LauncherConsumerFSM.ConsumerFSM(this, consumerData));
    }
}
