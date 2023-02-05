package lab.lab2;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class InitiatorBehaviour extends Behaviour {
    boolean exit;
    double x, delta;
    List<AID> receivers = new ArrayList<>();
    private final List<String> agents = Arrays.asList("FirstAgent", "SecondAgent", "ThirdAgent");
    private final List<Double> funcResultFirst = new ArrayList<>(), funcResultSecond = new ArrayList<>(), funcResultThird = new ArrayList<>();

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.or(MessageTemplate.MatchProtocol("ResponseFromSlave"),
                MessageTemplate.MatchProtocol("Initiator")));
        if (msg != null) {
            if (msg.getProtocol().equals("Initiator")) {

                List<String> xANDdelta = new ArrayList<>(List.of(msg.getContent().split(",")));
                x = Double.parseDouble(xANDdelta.get(0));
                delta = Double.parseDouble(xANDdelta.get(1));
                List<Double> points = List.of(x - delta, x, x + delta);

//                log.info("{} is the initiator", myAgent.getLocalName());
                switch (myAgent.getLocalName()) {
                    case "FirstAgent" -> points.forEach(x -> funcResultFirst.add(FunctionAgent.Calculation("FuncFirst", x)));
                    case "SecondAgent" -> points.forEach(x -> funcResultSecond.add(FunctionAgent.Calculation("FuncSecond", x)));
                    case "ThirdAgent" -> points.forEach(x -> funcResultThird.add(FunctionAgent.Calculation("FuncThird", x)));
                }
                agents.forEach(agent -> {
                    if (!myAgent.getLocalName().equals(agent))
                        receivers.add(new AID(agent, false));
                });
                myAgent.addBehaviour(new ProvideParametersBehaviour(myAgent,3000, receivers, x, delta));
            }

            if (msg.getProtocol().equals("ResponseFromSlave")) {
                switch (msg.getSender().getLocalName()) {
                    case "FirstAgent" -> Arrays.stream(msg.getContent().split(", ")).forEach(Fx1 -> funcResultFirst.add(Double.parseDouble(Fx1)));
                    case "SecondAgent" -> Arrays.stream(msg.getContent().split(", ")).forEach(Fx2 -> funcResultSecond.add(Double.parseDouble(Fx2)));
                    case "ThirdAgent" -> Arrays.stream(msg.getContent().split(", ")).forEach(Fx3 -> funcResultThird.add(Double.parseDouble(Fx3)));
                }
            }

            if (funcResultFirst.size() == 3 && funcResultSecond.size() == 3 && funcResultThird.size() == 3) {
                List<Double> funcValue = new ArrayList<>();
                IntStream.range(0, 3).forEach(index -> funcValue.add(funcResultFirst.get(index) + funcResultSecond.get(index) + funcResultThird.get(index)));
                funcResultFirst.clear();
                funcResultSecond.clear();
                funcResultThird.clear();
                receivers.clear();

                double intermediateValueFunction = funcValue.get(0);
                int maxIndex = 0;
                for (int i = 0; i < funcValue.size(); i++) {
                    if (funcValue.get(i) > intermediateValueFunction) {
                        intermediateValueFunction = funcValue.get(i);
                        maxIndex = i;
                    }
                }
                switch (maxIndex) {
                    case 0 -> check(x - delta, delta, maxIndex, funcValue);
                    case 1 -> check(x, delta / 2, maxIndex, funcValue);
                    case 2 -> check(x + delta, delta, maxIndex, funcValue);
                }
            }
        }
        else block();
    }

    public void check(double x, double delta, int maxIndex, List<Double> funcValue) {
//        log.info("Intermediate value of the function: {}, The value of X = {}, The value of Delta = {}",
//                funcValue.get(maxIndex), x, delta);
        if (delta < 0.01) {
            log.info("""
                 Sufficient accuracy of calculations has been achieved!
                 Maximum value of the function: {}
                 The value of X = {}
                 The value of Delta = {}""", funcValue.get(maxIndex), x, delta);
            exit = true;
        }
        else myAgent.addBehaviour(new Transfer(myAgent, 1000, x, delta));
    }

    @Override
    public boolean done() {
        return exit;
    }
}