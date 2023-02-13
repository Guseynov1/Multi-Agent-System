package lab4.Agent;

import jade.core.Agent;
import lab4.Behaviour.Distributer.ReceiveTaskToAuction;

public class Distributer extends Agent {

    @Override
    protected void setup() {
        addBehaviour(new ReceiveTaskToAuction());
    }
}

