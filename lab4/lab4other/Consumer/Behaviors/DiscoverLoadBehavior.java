package lab.lab4other.Consumer.Behaviors;

import lab.lab4other.Master.Meeting;
import lab.lab4other.Master.Definition;
import lab.lab4other.Master.TimeHandler;
import jade.core.behaviours.OneShotBehaviour;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Поведение Агента-Потребителя
 * По XML файлу сверяет график нагрузки и имеющиеся запасы ЭЭ
 * Если ЭЭ недостаточно, то запускает диалог с Агентом-распределителем энергии
 * В этом же поведении принимает сообщение от Распределителя о количестве закупленной ЭЭ
 */
@Slf4j
public class DiscoverLoadBehavior extends OneShotBehaviour {

    @Override
    public void action() {
        // актуальное виртуальное время и время до конца часа
        int currentTime = (int) TimeHandler.getCurrentHour();
        int tillTime = (int) TimeHandler.calcMillisTillNextHour();
        // забирает из xml-файла значение необходимой энергии на следующий час
        double energyLess = parser(currentTime, getAgent().getLocalName());
        if (energyLess != 0) {
            log.info("У агента {} на момент {} нехватка {}", getAgent().getLocalName(), currentTime, energyLess);
            getAgent().addBehaviour(new ActiveBehavior(new SendRequestAndWaitBehavior(energyLess), new TimeToEatBehavior(myAgent, tillTime)));
        }
    }

    public static double parser(int time, String agentName) {
        double value = 0;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Meeting.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Meeting object = (Meeting) jaxbUnmarshaller.unmarshal(new File("src/main/resources/firstConsumer.xml"));;
            for (Definition def : object.getDefinitions()) {
                // возвращает из массива значений нагрузки нагрузку на актуальный час
                if (def.getAgent().equals(agentName)) value = def.getFile().get(time);
            }
        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
        return value;
    }
}

