package lab.lab4new.Agents;

import jade.core.Agent;
import lab.lab4new.producer.ProducerBeh;
import lab.lab4new.producer.ProducerGetOrderFromDistributorBeh;
import lab.lab4new.producer.ProducerGetReqFromDistributorBehavior;
import lab.lab4new.producer.ProducerWaitStartChat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static lab.lab4new.Agents.Smith.registration;

@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class Producer extends Agent {
    private Double energy, startCost, minCost = 0d;

    @Override
    @SneakyThrows
    protected void setup() {

        log.info("{} запущен", this.getLocalName());
        switch (this.getLocalName()) {
            case "ТЭС" -> this.addBehaviour(new ProducerBeh("ТЭС", 9.6));
            case "ВЭС" -> this.addBehaviour(new ProducerBeh("ВЭС", 7.2, 4.8));
            case "СЭС" -> this.addBehaviour(new ProducerBeh("СЭС", -86.884, 22.344, -1.450, 0.027));
        }

        this.addBehaviour(new ProducerGetReqFromDistributorBehavior());
        this.addBehaviour(new ProducerWaitStartChat());
        this.addBehaviour(new ProducerGetOrderFromDistributorBeh());
        // регистрация в yellow pages. Агент производитель предоставляет сервис Producer
        registration("Producer", getAID(), getLocalName(), this);
    }
}