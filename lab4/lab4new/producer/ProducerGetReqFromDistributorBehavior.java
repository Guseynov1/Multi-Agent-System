package lab.lab4new.producer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4new.Agents.Producer;
import lombok.extern.slf4j.Slf4j;
import lab.lab4new.Auto.Parameters.Color;

@Slf4j
public class ProducerGetReqFromDistributorBehavior extends Behaviour {

    @Override
    public void action() {
        ACLMessage request = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        Producer agent = (Producer) myAgent;
        if (request != null) {
            double energyRequestF = Double.parseDouble(request.getContent().split(";")[0]);
            double energyRequestS = Double.parseDouble(request.getContent().split(";")[1]);
            double energyRequestT = Double.parseDouble(request.getContent().split(";")[2]);
            double currentEnergy = agent.getEnergy();

            if (request.getPerformative() == ACLMessage.REQUEST) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info(Color.GREEN + "Запрашивают -> [{} | {} | {}], {}: в наличии -> {}" + Color.RESET,
                        energyRequestF, energyRequestS, energyRequestT, myAgent.getLocalName(), currentEnergy);
                ACLMessage response = request.createReply();
                if (energyRequestF > currentEnergy & energyRequestS > currentEnergy & energyRequestT > currentEnergy) {
                    response.setPerformative(ACLMessage.REFUSE);
                } else {
                    response.setPerformative(ACLMessage.PROPOSE);
                    response.setContent(energyRequestF + ";" + energyRequestS + ";" + energyRequestT + ";" + agent.getStartCost());
                }
                myAgent.send(response);
            }
        } else block();
    }

    @Override
    public boolean done() {
        return false;
    }
}