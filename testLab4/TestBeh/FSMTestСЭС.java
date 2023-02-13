package testLab4.TestBeh;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import lab4.Config.Data;
import lab4.Config.GenerationData;
import lab4.Helper.DfHelper;

import java.util.ArrayList;
import java.util.List;

public class FSMTestСЭС extends FSMBehaviour {
    Data producerDataСЭС;

    public FSMTestСЭС(Data producerDataСЭС) {
        this.producerDataСЭС = producerDataСЭС;

        registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                List<String> AgentsInAllTrade = new ArrayList<>();
                AgentsInAllTrade.add("Production");
                DfHelper.registerItself(AgentsInAllTrade, getAgent());
            }
        }, "Register");
        registerLastState(new GenerationData(getAgent(),20000, null, null, producerDataСЭС), "Parsing");
        registerDefaultTransition("Register", "Parsing");
    }

}
