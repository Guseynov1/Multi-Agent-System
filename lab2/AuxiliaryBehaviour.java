package lab.lab2;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class AuxiliaryBehaviour extends Behaviour {
    double x, delta;
    boolean exit;

    @Override
    public void action() {
        ACLMessage initiator = myAgent.receive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                MessageTemplate.MatchProtocol("CalcFunction")));
        if(initiator != null) {

            List<String> xANDdelta = List.of(initiator.getContent().split(", "));
            x = Double.parseDouble(xANDdelta.get(0));
            delta = Double.parseDouble(xANDdelta.get(1));
            List<Double> points = List.of(x - delta, x, x + delta);

            List<Double> funcResult = new ArrayList<>();
            switch (myAgent.getLocalName()) {
                case "FirstAgent" -> points.forEach(x -> funcResult.add(FunctionAgent.Calculation("FuncFirst", x)));
                case "SecondAgent" -> points.forEach(x -> funcResult.add(FunctionAgent.Calculation("FuncSecond", x)));
                case "ThirdAgent" -> points.forEach(x -> funcResult.add(FunctionAgent.Calculation("FuncThird", x)));
            }
            ACLMessage response = new ACLMessage(ACLMessage.REQUEST_WHEN);
            response.setProtocol("ResponseFromSlave");
            response.setContent(funcResult.get(0) + ", " + funcResult.get(1) + ", " + funcResult.get(2));
            response.addReceiver(new AID(initiator.getSender().getLocalName(), false));
            myAgent.send(response);
        }
        else block();
    }

    @Override
    public boolean done() {
        return exit;
    }
}