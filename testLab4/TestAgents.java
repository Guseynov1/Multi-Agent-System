package testLab4;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class TestAgents extends Agent {
    @Override
    protected void setup() {
        log.info("{} was born", this.getLocalName());
        Object[] arguments = getArguments();
        Arrays.stream(arguments).forEach(argument -> addBehaviour((Behaviour) argument));
    }
}
