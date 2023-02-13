//package lab.lab4.Master;
//
//import jade.core.ProfileImpl;
//import jade.core.Runtime;
//import jade.util.leap.Properties;
//import jade.wrapper.AgentContainer;
//import jade.wrapper.AgentController;
//import lombok.SneakyThrows;
//
//public class Container {
//
//    private static AgentContainer agentContainer;
//
//    public static void main(String[] args) {
//
//        Properties properties = new Properties();
//        properties.setProperty("gui", "true");
//        ProfileImpl profile = new ProfileImpl(properties);
//        Runtime.instance().setCloseVM(true);
//        agentContainer = Runtime.instance().createMainContainer(profile);
//        createAgents();
//
//    }
//
//    @SneakyThrows
//    public static void createAgents() {
//        AgentController T = agentContainer.createNewAgent("ТЭС", ProducerAgent.class.getName(), new Object[]{});
//        AgentController B = agentContainer.createNewAgent("ВЭС", ProducerAgent.class.getName(), new Object[]{});
//        AgentController S = agentContainer.createNewAgent("СЭС", ProducerAgent.class.getName(), new Object[]{});
//
//        AgentController distributor_1 = agentContainer.createNewAgent("Distributor-1", DistributorAgent.class.getName(), new Object[]{});
//        AgentController consumer_1 = agentContainer.createNewAgent("Consumer-1", ConsumerAgent.class.getName(), new Object[]{});
//        AgentController master = agentContainer.createNewAgent("master.Master-1", Master.class.getName(), new Object[]{});
//
//        T.start();
//        B.start();
//        S.start();
//        distributor_1.start();
//        consumer_1.start();
//        master.start();
//    }
//}
