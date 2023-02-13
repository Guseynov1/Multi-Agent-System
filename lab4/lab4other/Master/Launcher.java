package lab.lab4other.Master;
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Launcher extends Agent {

    @Override
    protected void setup() {
        String path = getArguments()[0].toString();
        Meeting meeting = xmlHandler.unMarshalAny(Meeting.class, path);
        assert meeting != null;
        for (Definition definition : meeting.getDefinitions()) {
            try {
                AgentController createdAgent = getContainerController().
                        createNewAgent(definition.getAgent(), definition.getClazz(), new Object[]{definition.getFile()});
                createdAgent.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }
}
