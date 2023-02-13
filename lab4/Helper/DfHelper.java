package lab4.Helper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DfHelper {

    public static List<AID> searchForAgents(String serviceToSearch, Agent agent){
        DFAgentDescription dfad = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        List<AID> agents = new ArrayList<>();
        sd.setType(serviceToSearch);
        dfad.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(agent, dfad);
            Arrays.stream(result).forEach(res -> agents.add(res.getName()));
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return agents;
    }

    public static void registerItself(List<String> serviceName, Agent theAgent){
        DFAgentDescription dfad = new DFAgentDescription();
        dfad.setName(theAgent.getAID());
        for (String s : serviceName) {
            ServiceDescription sd = new ServiceDescription();
            sd.setType(s);
            sd.setName(theAgent.getLocalName() + "-" + s);
            dfad.addServices(sd);
        }
        try {
            DFService.register(theAgent, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public static void registration(String type, AID name, String local, Agent agent) {
        DFAgentDescription dfad = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        dfad.setName(name);
        sd.setName(local);
        sd.setType(type);
        dfad.addServices(sd);
        try {
            DFService.register(agent, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public static AID createTopic(Agent agent, String topicName){
        AID jadeTopic = null;
        try {
            TopicManagementHelper topicHelper = (TopicManagementHelper) agent.getHelper(TopicManagementHelper.SERVICE_NAME);
            jadeTopic = topicHelper.createTopic(topicName);
            topicHelper.register(jadeTopic);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return jadeTopic;
    }
}
