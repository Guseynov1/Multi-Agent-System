package lab.lab4other.Producer;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class ProducerAgent extends Agent {

    private Double energy, startCost, minCost = 0d;

    @Override
    @SneakyThrows
    protected void setup() {
        log.info("{} запущен", this.getLocalName());
        switch (this.getLocalName()) {
            case "ТЭС" -> this.addBehaviour(new ProducerBehavior("ТЭС", 15.7));
            case "ВЭС" -> this.addBehaviour(new ProducerBehavior("ВЭС", 7.2, 8.3));
            case "СЭС" -> this.addBehaviour(new ProducerBehavior("СЭС", -78.985, 20.313, -1.3185, 0.0247));
        }
//        this.addBehaviour(new ProducerGetReqFromDistributorBehavior());
//        this.addBehaviour(new ProducerWaitStartChat());
//        this.addBehaviour(new ProducerGetOrderFromDistributorBeh());

        // регистрация в yellow pages. Агент производитель предоставляет сервис Producer
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("Producer");
        dfd.addServices(sd);
        DFService.register(this, dfd);
    }

    public void generate(double generatedEnergy) {
        energy = energy + generatedEnergy;
    }
}