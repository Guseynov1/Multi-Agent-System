package lab.lab2;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.pow;

@Slf4j
public class FunctionAgent extends Agent {
    Random random = new Random();
    double x = Math.random();
    double delta = 0.5;
    List<String> agents = List.of("SecondAgent", "ThirdAgent");
    int initiator = random.nextInt(agents.size());

    @Override
    protected void setup() {
        if (getLocalName().equals("FirstAgent")){
            log.info("""
                    {} is the initiator
                    The value of x = {}
                    The value of delta = {}""", getLocalName(), x, delta);
            ACLMessage initTrans = new ACLMessage(ACLMessage.INFORM);
            initTrans.setProtocol("Initiator");
            initTrans.setContent(x + ", " + delta);
            initTrans.addReceiver(new AID(agents.get(initiator), false));
            send(initTrans);
        }
        addBehaviour(new AuxiliaryBehaviour());
        addBehaviour(new InitiatorBehaviour());
    }

    public static double Calculation(@NotNull String funcNum, double x) {
        double function = 0;
        return switch (funcNum) {
            case ("FuncFirst") -> -(0.5 * pow(x, 2)) - 4;
            case ("FuncSecond") -> pow(2, -(0.1 * x));
            case ("FuncThird") -> cos(x);
            default -> function;
        };
    }
}
