package lab.lab4other.Distributor.Behaviors;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lab.lab4other.Master.TimeHandler;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class GetBetBehavior extends Behaviour {

    private int pieceOfPie = 1;
    private AID topic;
    private double quantity;
    private boolean keyExit, getTest;
    private int till, tilt;
    double initialBet = 500_000;
    private double bestBet = initialBet;

    public GetBetBehavior(double quantity, int till) {
        this.quantity = quantity;
        this.tilt = till;
    }

    @Override
    public void onStart() {
        keyExit = false;
        getTest = false;
        till = tilt;
        initialBet = 500_000;
        bestBet = initialBet;

        // ждем получения названия топика, чтобы начать сканирование лучшей цены
        while (!getTest) {
            ACLMessage receive = getAgent().receive(MessageTemplate.MatchProtocol("Test"));
            if (receive != null) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getTest = true;
                topic = new AID(receive.getContent(), true);
                // начальная ставка - флаг, от которой начинают отталкиваться производителя в торгах
                convertInMSG(topic, String.valueOf(initialBet), "getBet");
                // создаем Waker, заканчивающий торги и помогающий впоследствии объявить победителя
                getAgent().addBehaviour(new BetEndingBehavior(myAgent, 15000, topic));
            }
        }
    }

    @Override
    public void action() {
        ACLMessage msg = getAgent().receive(MessageTemplate.and(MessageTemplate.MatchTopic(topic), MessageTemplate.MatchProtocol("getBet")));
        if (msg != null) {
            double contentBet = Double.parseDouble(msg.getContent());
            if (contentBet < bestBet) {
                bestBet = contentBet;
                convertInMSG(topic, String.valueOf(bestBet), "getBet");
            }
        }

        ACLMessage receive = getAgent().receive(MessageTemplate.and(MessageTemplate.MatchTopic(topic), MessageTemplate.MatchProtocol("KeyExit")));
        if (receive != null) {
            quantity -= pieceOfPie;
            keyExit = true;
        }
    }

    public boolean done() {
        return keyExit;
    }

    @Override
    public int onEnd() {
        if (bestBet != initialBet) convertInMSG(stack(getAgent().getLocalName()), String.valueOf(pieceOfPie), "get");
        else log.info("Empty topic {}", topic.getLocalName());
        // переменная, следящая за истечением срока удовлетворения нужд потребителя
        till -= (System.currentTimeMillis() - TimeHandler.startTime) % TimeHandler.hourDuration;
        long limit = 18000;
        if (quantity > 0) { // нужды потребителя не восполнены в должной мере
            if (till < limit) { // дистрибьютор не успевает уложиться, прерываем ФСМ
                System.out.println("Времени мало!");
                return 0;
            }
            // времени еще
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // идем на новый круг торгов
            return 1;
        }
        // все нужды удовлетворены, FSM-поведение заканчивается
        return 0;
    }

    /**
     * метод для конвертации данных в сообщение
     * @param agentTarget получатель сообщения
     * @param content содержимое сообщения
     * @param protocol протокол
     */
    void convertInMSG(AID agentTarget, String content, String protocol) {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(agentTarget);
        msg.setContent(content);
        msg.setProtocol(protocol);
        getAgent().send(msg);
    }

    private AID stack(String agentName) {
        return switch (agentName) {
            case "AgentDistributor1" -> new AID("AgentConsumer1", false);
            case "AgentDistributor2" -> new AID("AgentConsumer2", false);
            case "AgentDistributor3" -> new AID("AgentConsumer3", false);
            default -> null;
        };
    }
}
