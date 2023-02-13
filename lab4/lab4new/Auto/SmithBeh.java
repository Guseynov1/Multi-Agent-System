package lab.lab4new.Auto;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.SneakyThrows;

import java.util.Arrays;

public class SmithBeh extends TickerBehaviour {

    private final String[] producers, consumers;
    public SmithBeh(Agent agent, long period, String[] producers, String[] consumers) {
        super(agent, period);
        this.producers = producers;
        this.consumers = consumers;
        System.out.println(Arrays.toString(producers) + " - Производители");
        System.out.println(Arrays.toString(consumers) + " - Потребители [ЦветМет, ЖилоеЗдание, ПищПром]");
    }

    @Override
    @SneakyThrows
    protected void onTick() {
        System.out.println("\n====================================================================================\n");
        ACLMessage start = new ACLMessage(ACLMessage.PROPAGATE);
        for (String producer : producers) start.addReceiver(new AID(producer, false));
        myAgent.send(start);
        Thread.sleep(100);
        start = new ACLMessage(ACLMessage.PROPAGATE);
        for (String consumer : consumers) start.addReceiver(new AID(consumer, false));
        myAgent.send(start);
    }
}