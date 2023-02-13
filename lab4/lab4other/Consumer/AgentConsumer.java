package lab.lab4other.Consumer;

import lab.lab4other.Consumer.Behaviors.DiscoverLoadBehavior;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

/**
 * Класс агента потребителя
 * Имеет график нагрузки в XML виде, связанный со временем
 * Для покрытия этого графика делает запрос Агенту-Распределителю
 */
@Slf4j
public class AgentConsumer extends Agent {

    @Override
    protected void setup() {
        log.info("{} starting to consume", getLocalName());
        addBehaviour(new DiscoverLoadBehavior());
    }
}







