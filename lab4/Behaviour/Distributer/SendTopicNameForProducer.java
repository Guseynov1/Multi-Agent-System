package lab4.Behaviour.Distributer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.DistributerConfig;
import lab4.Config.Data;
import lab4.Helper.DfHelper;
import lab4.Helper.XMLHelper;

import java.util.List;

public class SendTopicNameForProducer extends OneShotBehaviour {
    Data data;
    List<AID> agents;

    public SendTopicNameForProducer(Agent agent, Data data) {
        super(agent);
        this.data = data;
    }

    @Override
    public void onStart() {
        agents = DfHelper.searchForAgents("Production", getAgent());
        data.setNumberOfProducers(agents.size());
    }

    @Override
    public void action() {
        DistributerConfig distributerConfig = XMLHelper.unMarshalAny(DistributerConfig.class, getAgent().getLocalName() + ".xml");
        AID topic = DfHelper.createTopic(getAgent(), distributerConfig.getTopicName());
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        data.setTopic(topic);
        msg.setProtocol("topicName");
        agents.forEach(msg::addReceiver);
        msg.setContent(distributerConfig.getTopicName());
        getAgent().send(msg);
    }
}
