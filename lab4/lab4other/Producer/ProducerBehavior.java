package lab.lab4other.Producer;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProducerBehavior extends Behaviour {

    String type;
    double A, B1, B2, C0, C1, C2, C3;
    int hour = 1;

    public ProducerBehavior(String type, double A) {
        this.type = type;
        this.A = A;
    }

    public ProducerBehavior(String type, double B1, double B2) {
        this.type = type;
        this.B1 = B1;
        this.B2 = B2;
    }

    public ProducerBehavior(String type, double C0, double C1, double C2, double C3) {
        this.type = type;
        this.C0 = C0;
        this.C1 = C1;
        this.C2 = C2;
        this.C3 = C3;
    }


    @Override
    public void action() {

        ACLMessage start = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
        if (start != null) {
            switch (type) {
                case "ТЭС" -> {
                    ProducerAgent producerAgent = (ProducerAgent) this.getAgent();
                    Double energy = A;
                    producerAgent.setEnergy(energy);
                    producerAgent.setMinCost(3.0);
                    producerAgent.setStartCost(4.8d);
                    log.info("Энергия ТЭС {}", producerAgent.getEnergy());
                    hour++;
                    if (hour == 24) hour = 1;
                }
                case "ВЭС" -> {
                    ProducerAgent producerAgent = (ProducerAgent) this.getAgent();
                    double energy = (1 / (B2 * Math.sqrt(2 * Math.PI)) * Math.exp(-(hour - B1) * (hour - B1) / (2 * B2 * B2))) * 250;
                    double startCost = 50 / (energy + 2);
                    if (startCost > 7) startCost = 8d;
                    producerAgent.setStartCost(startCost);
                    producerAgent.setMinCost(startCost / 2);
                    producerAgent.setEnergy(energy);
                    System.out.println("Энергия ВЭС " + producerAgent.getEnergy());
                    hour++;
                    if (hour == 24) hour = 1;
                }
                case "СЭС" -> {
                    ProducerAgent producerAgent = (ProducerAgent) this.getAgent();
                    double energy;
                    double startCost = 100d;
                    if (hour > 5 && hour < 19) {
                        energy = C0 + C1 * hour + C2 * hour * hour + C3 * hour * hour * hour;
                        startCost = 40 / (energy + 2);
                    } else energy = 0d;
                    producerAgent.setStartCost(startCost);
                    producerAgent.setMinCost(startCost / 2);
                    producerAgent.setEnergy(energy);
                    log.info("Энергия СЭС {}", producerAgent.getEnergy());
                    hour++;
                    if (hour == 24) hour = 1;
                }
            }
        } else block();
    }

    // регистрация производителя в желтой книге
    static void registration(Agent agent) {
        DFAgentDescription dfad = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        dfad.setName(agent.getAID());
        sd.setType(Services.Producer.toString());
        sd.setName(Services.Producer + agent.getLocalName());
        dfad.addServices(sd);
        try {
            DFService.register(agent, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}