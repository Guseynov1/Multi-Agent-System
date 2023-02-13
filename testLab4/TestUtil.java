package testLab4;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.List;

public class TestUtil {
    private AgentContainer mainContainer;
    public void startJade(List<String> services){

        ProfileImpl profileIMPL = new ProfileImpl();
        profileIMPL.setParameter("gui", "true");
        StringBuilder sb = new StringBuilder();
        for (String service : services) {
            sb.append(service).append(";");
        }
        profileIMPL.setParameter("services", sb.toString());
        Runtime.instance().setCloseVM(true);

        mainContainer = Runtime.instance().createMainContainer(profileIMPL);
    }
    public void CreateAgent(String name, Behaviour... bhs) {
        try {
            AgentController newAgent = mainContainer.createNewAgent(name, TestAgents.class.getName(), bhs);
            newAgent.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
