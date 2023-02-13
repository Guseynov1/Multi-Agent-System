package lab.lab4other.Consumer.Behaviors;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ActiveBehavior extends ParallelBehaviour {
    private Behaviour buyEnergy;
    private WakerBehaviour stopBuying;

    @Override
    public void onStart() {
        addSubBehaviour(buyEnergy);
        addSubBehaviour(stopBuying);
    }
}
