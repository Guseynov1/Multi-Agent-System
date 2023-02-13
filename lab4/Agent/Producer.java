package lab4.Agent;

import jade.core.Agent;
import lab4.Behaviour.Producer.ReceiveTask;
import lab4.Config.GenerationData;
import lab4.Config.Data;

import static lab4.Helper.DfHelper.registration;

public class Producer extends Agent {
    Data producerData = new Data();

    @Override
    protected void setup() {
        registration("Production", getAID(), getLocalName(), this);
        switch (this.getLocalName()){
            case "ТЭС" -> addBehaviour(new GenerationData(this, 75000, producerData, null, null));
            case "СЭС" -> addBehaviour(new GenerationData(this, 75000, null, null, producerData));
            case "ВЭС" -> addBehaviour(new GenerationData(this, 75000, null, producerData, null));
        }
        addBehaviour(new ReceiveTask(producerData));
    }
}
