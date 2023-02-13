package lab.lab4other.Distributor.Behaviors;

import lab.lab4other.Master.TimeHandler;
import jade.core.AID;
import jade.core.ServiceException;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Поведение Агента-Дистрибьютора
 * FSM-поведение выполняет функции создания топика и поиска победителей в торгах:
 * 1. FirstState: создание топика и приглашение в него потенциальных участников
 * 2. Ведение учета лучшей цены во время ведения торгов между производителями.
 * Если в результате торгов вся нужная ЭЭ для потребителя была закуплена, то FSM-поведение завершается, если нет - то поведение идет на новый цикл торгов
 */
@Slf4j
@RequiredArgsConstructor
public class TraderBehaviorFSM extends FSMBehaviour {

    GetBetBehavior getBetBehavior = new GetBetBehavior();
    private final double quantity;
    private DFAgentDescription dfad;
    private int tillTime = (int) TimeHandler.calcMillisTillNextHour();

    @Override
    public void onStart() {
        registerFirstState(new OneShotBehaviour() {
            public void action() {
                // зовем в топик
                AID aid = createTopic();
                try {
                    findAndSend(aid);
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
                log.info("{} created a topic {}", getAgent().getLocalName(), aid.getName());
                getBetBehavior.convertInMSG(getAgent().getAID(), aid.getName(), "Test");
            }
        }, "Start");

        // активная фаза торгов
        registerState(new GetBetBehavior(quantity, tillTime), "Gap");
        // это условие должно выполняться при полном пополнении ЭЭ либо при досрочном выходе по истечении времени
        registerLastState(new OneShotBehaviour() {
            public void action() {
                log.info("Stop bargains");
            }
        }, "End");
        // Поскольку после первого состояния должно быть выполнено второе и никакого ветвления не подразумевается, используем
        registerDefaultTransition("Start", "Gap", new String[]{"Start", "Gap"});
        registerTransition("Gap", "Start", 1);
        registerTransition("Gap", "End", 0);
    }

    private AID createTopic() {
        AID jadeTopic = null;
        try {
            TopicManagementHelper topicHelper = (TopicManagementHelper) getAgent().getHelper(TopicManagementHelper.SERVICE_NAME);
            // создаем топик со случайным именем
            double random = Math.random() * 100;
            jadeTopic = topicHelper.createTopic("Test" + random);
            topicHelper.register(jadeTopic);
        } catch (ServiceException se) {
            se.printStackTrace();
        }
        return jadeTopic;
    }

    private void findAndSend(AID aid) throws FIPAException {
        for (DFAgentDescription foundAgent : DFService.search(myAgent, dfad)) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setProtocol("Registration");
            msg.setContent(aid.getLocalName());
            msg.addReceiver(foundAgent.getName());
            getAgent().send(msg);
        }
    }
}

