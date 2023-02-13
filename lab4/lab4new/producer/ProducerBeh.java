package lab.lab4new.producer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Producer;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class ProducerBeh extends Behaviour {
    String type;
    double A, B1, B2, C0, C1, C2, C3;
    int timer = 1;


    public ProducerBeh(String type, double A) {
        this.type = type;
        this.A = A;
    }

    public ProducerBeh(String type, double B1, double B2) {
        this.type = type;
        this.B1 = B1;
        this.B2 = B2;
    }

    public ProducerBeh(String type, double C0, double C1, double C2, double C3) {
        this.type = type;
        this.C0 = C0;
        this.C1 = C1;
        this.C2 = C2;
        this.C3 = C3;
    }


    @Override
    public void action() {
        if (myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE)) != null) {
            switch (type) {
                case "ТЭС" -> {
                    Producer producer = (Producer) this.getAgent();
                    Double energy = A;
                    producer.setEnergy(energy);
                    producer.setMinCost(3.0);
                    producer.setStartCost(4.8d);
                    log.info("Энергия ТЭС {}", producer.getEnergy());
                    timer++;
                    if (timer == 24) timer = 1;
                }
                case "ВЭС" -> {
                    Producer producer = (Producer) this.getAgent();
                    double energy = (1 / (B2 * Math.sqrt(2 * Math.PI)) * Math.exp(-(timer - B1) * (timer - B1) / (2 * B2 * B2))) * 250;
                    double startCost = 50 / (energy + 2);
                    if (startCost > 7) startCost = 8d;
                    producer.setStartCost(startCost);
                    producer.setMinCost(startCost / 2);
                    producer.setEnergy(energy);
                    log.info("Энергия ВЭС {}", producer.getEnergy());
                    timer++;
                    if (timer == 24) timer = 1;
                }
                case "СЭС" -> {
                    Producer producer = (Producer) this.getAgent();
                    double energy;
                    double startCost = 100d;
                    if (timer > 5 && timer < 19) {
                        energy = C0 + C1 * timer + C2 * timer * timer + C3 * timer * timer * timer;
                        startCost = 40 / (energy + 2);
                    } else energy = 0d;
                    producer.setStartCost(startCost);
                    producer.setMinCost(startCost / 2);
                    producer.setEnergy(energy);
                    log.info("Энергия СЭС {}", producer.getEnergy());
                    timer++;
                    if (timer == 24) timer = 1;
                }
            }
        } else block();
    }

    @Override
    public boolean done() {
        return false;
    }
}