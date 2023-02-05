package lab.lab3.behaviour;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Parallelism extends ParallelBehaviour {
    private final Behaviour TheBestChoice;
    private final WakerBehaviour TerminationOfWork;

    @Override
    public void onStart() {
        addSubBehaviour(TheBestChoice);
        addSubBehaviour(TerminationOfWork);
    }
}
