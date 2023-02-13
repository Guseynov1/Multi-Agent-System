package lab4.Behaviour.Distributer.FSM.Division;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import lab4.Behaviour.Distributer.FSM.ReceivingPricesFromProducer;
import lab4.Config.Data;

public class ParallelAfterDivision extends ParallelBehaviour {
    Data pricesForDistributerData;
    Data onEnd = new Data();

    public ParallelAfterDivision(Agent agent, Data pricesForDistributerData, long wakeUpTime) {
        super(agent, WHEN_ANY);
        this.pricesForDistributerData = pricesForDistributerData;
        addSubBehaviour(new ReceivingPricesFromProducer(getAgent(), pricesForDistributerData, onEnd));
        addSubBehaviour(new WakerBehaviour(getAgent(), wakeUpTime) {
            @Override
            protected void onWake() {
                super.onWake();
            }
        });
    }
    @Override
    public int onEnd() {
        return onEnd.getOnEnd();
    }


    public static class Parallel extends ParallelBehaviour {
        Data pricesForDistributerData;
        Data onEnd = new Data();

        public Parallel(Agent agent, Data pricesForDistributerData, long wakeUpTime) {
            super(agent, WHEN_ANY);
            this.pricesForDistributerData = pricesForDistributerData;
            addSubBehaviour(new ReceivingPricesFromProducer(getAgent(), pricesForDistributerData, onEnd));
            addSubBehaviour(new WakerBehaviour(getAgent(), wakeUpTime) {
                @Override
                protected void onWake() {
                    super.onWake();
                }
            });
        }
        @Override
        public int onEnd() { return onEnd.getOnEnd(); }
    }
}
