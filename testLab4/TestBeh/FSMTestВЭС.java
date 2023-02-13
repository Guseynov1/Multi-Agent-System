package testLab4.TestBeh;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import lab4.Config.Data;
import lab4.Config.GenerationData;
import lab4.Helper.DfHelper;

import java.util.ArrayList;
import java.util.List;

public class FSMTestВЭС extends FSMBehaviour {
    Data producerDataВЭС;

    public FSMTestВЭС(Data producerDataВЭС) {
        this.producerDataВЭС = producerDataВЭС;

        registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                List<String> AgentsInAllTrade = new ArrayList<>();
                AgentsInAllTrade.add("Production");
                DfHelper.registerItself(AgentsInAllTrade, getAgent());
            }
        }, "Register");
        registerLastState(new GenerationData(getAgent(),20000, null, producerDataВЭС, null), "Parsing");
        registerDefaultTransition("Register", "Parsing");
    }

}
