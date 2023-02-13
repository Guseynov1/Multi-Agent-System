package lab.lab4other.Distributor;

import lab.lab4other.Distributor.Behaviors.WaitConsumer;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс Агента-Распределителя связывает Агента-Производителя и Агента-Потребителя
 * При получении запроса на покупку ЭЭ обращается к Агентам-Производителям с предложением организовать торги.
 * Он же их и заканчивает, выбирая наилучшую цену и заключая контракт с Производителем. Передает купленную ЭЭ обратно Потребителю
 */
@Slf4j
public class AgentDistributor extends Agent {
    @Override
    protected void setup() {
        log.info("I'm {}", getLocalName());
        addBehaviour(new WaitConsumer());
    }
}
