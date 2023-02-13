package lab.lab4other.Producer;


import lab.lab4other.Producer.Behaviors.WaitInviteBehavior;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

import static lab.lab4other.Producer.ProducerBehavior.registration;


// Класс Агента-Производителя. Производит ЭЭ, по предложению Агента-Распределителя участвует в торгах за право продать ему ЭЭ

@Slf4j
public class AgentProducer extends Agent {

    @Override
    protected void setup() {
        // создание нового объекта класса "батарея"
        Battery battery = new Battery(getLocalName());
        // начало накопления электроэнергии
        battery.charge();
        log.info("I'm producer {}!", getLocalName());
        registration(this);
        addBehaviour(new WaitInviteBehavior(battery));
    }

}

enum Services {
    Producer
}
