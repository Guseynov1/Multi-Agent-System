package lab.lab4new.Auto;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import lab.lab4new.Agents.Consumer;
import lab.lab4new.Agents.Distributor;
import lab.lab4new.Agents.Smith;
import lab.lab4new.Agents.Producer;
import lombok.SneakyThrows;

public class Main {

    private static AgentContainer agentContainer;

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("gui", "true");
        properties.put("jade_core_messaging_MessageManager_maxqueuesize", "1_000_000_000");
        ProfileImpl profile = new ProfileImpl(properties);
        Runtime.instance().setCloseVM(true);
        agentContainer = Runtime.instance().createMainContainer(profile);
        createAgents();
    }

    @SneakyThrows
    public static void createAgents() {
        create("ТЭС", "ВЭС", "СЭС", Producer.class);
        create("firstDistributor", "secondDistributor", "thirdDistributor", Distributor.class);
        create("firstConsumer", "secondConsumer", "thirdConsumer", Consumer.class);
        smith();
    }

    public static void create(String nickname1, String nickname2, String nickname3, Class clazz) throws StaleProxyException, InterruptedException {
        AgentController agentController1 = agentContainer.createNewAgent(nickname1, clazz.getName(), new Object[]{});
        AgentController agentController2 = agentContainer.createNewAgent(nickname2, clazz.getName(), new Object[]{});
        AgentController agentController3 = agentContainer.createNewAgent(nickname3, clazz.getName(), new Object[]{});
        agentController1.start();
        agentController2.start();
        agentController3.start();
        Thread.sleep(10);
    }
    private static void smith() throws StaleProxyException, InterruptedException {
        AgentController agentController = agentContainer.createNewAgent("Smith", Smith.class.getName(), new Object[]{});
        agentController.start();
        Thread.sleep(10);
    }
}