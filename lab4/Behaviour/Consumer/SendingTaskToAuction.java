package lab4.Behaviour.Consumer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lab4.Config.Parameters;
import lab4.Config.Parameters.Color;
import lab4.Config.Data;
import lab4.Helper.TimeHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
@Slf4j
public class SendingTaskToAuction extends WakerBehaviour {
    Data data;

    public SendingTaskToAuction(Agent agent, long timeout, Data data) {
        super(agent, timeout);
        this.data = data;
    }

    @Override
    protected void onWake() {
        Parameters parameters = getParameters(getAgent().getLocalName());
        List<Double> load = parameters.getLoad();
        log.info(Color.CYAN + "{}: My current load is {} it's {}:00 O'Clock" + Color.RESET,
                getAgent().getLocalName(), load.get(TimeHelper.getActualHour()), TimeHelper.getActualHour());
        ACLMessage task = new ACLMessage(ACLMessage.REQUEST);
        data.setConsumerLoad(load.get(TimeHelper.getActualHour()));
        task.setContent(load.get(TimeHelper.getActualHour()) + "," + data.getConsumerMaxPrice());
        task.setProtocol("Task");
        task.addReceiver(new AID (parameters.getDistributer(), false));
        getAgent().send(task);
    }

    @SneakyThrows
    public static Parameters getParameters(String name) {
        String cfgPath = "src/main/resources/" + name + ".xml";
        JAXBContext jaxbContext = JAXBContext.newInstance(Parameters.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Parameters) unmarshaller.unmarshal(new File(cfgPath));
    }
}
