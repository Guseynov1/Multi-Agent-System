package lab.lab2;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import lombok.SneakyThrows;

public class Main {

    private static AgentContainer agentContainer;

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("gui", "true");
        ProfileImpl profile = new ProfileImpl(properties);
        Runtime.instance().setCloseVM(true);
        agentContainer = Runtime.instance().createMainContainer(profile);
        createAgents("FirstAgent");
        createAgents("SecondAgent");
        createAgents("ThirdAgent");
    }

    @SneakyThrows
    public static void createAgents(String name) {
        AgentController number = agentContainer.createNewAgent(name, FunctionAgent.class.getName(), new Object[]{});
        number.start();
    }
}