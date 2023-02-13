package testLab4.TestBeh;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import lab4.Config.Data;
import lab4.Config.GenerationData;
import lab4.Helper.DfHelper;

import java.util.ArrayList;
import java.util.List;

public class FSMTestТЭС extends FSMBehaviour {
    Data producerDataТЭС;

    public FSMTestТЭС(Data producerDataТЭС) {
        this.producerDataТЭС = producerDataТЭС;

        registerFirstState(new OneShotBehaviour() {
            @Override
            public void action() {
                List<String> AgentsInAllTrade = new ArrayList<>();
                AgentsInAllTrade.add("Production");
                DfHelper.registerItself(AgentsInAllTrade, getAgent());
            }
        }, "Register");
        registerLastState(new GenerationData(getAgent(),20000, producerDataТЭС, null, null), "Parsing");
        registerDefaultTransition("Register", "Parsing");
    }

}
