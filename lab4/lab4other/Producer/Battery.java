package lab.lab4other.Producer;

import lab.lab4other.Master.TimeHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lab.lab4other.Consumer.Behaviors.DiscoverLoadBehavior.parser;

@Slf4j
@RequiredArgsConstructor
public class Battery {
    private static int turnInAhour = 6;
    private final String agentName;
    public double batteryCharge = 0;
    public void charge(){
        // забирает из xml-файла значение необходимой энергии на следующий час
        Thread tready = new Thread(() -> {
            while (true) {
                int currentHour = (int) TimeHandler.getCurrentHour();
                // забирает из xml-файла значение необходимой энергии на следующий час
                batteryCharge += parser(currentHour, agentName) / turnInAhour;
                log.info("Agent's current stock {}: {}", agentName, batteryCharge);
                try {
                    Thread.sleep(TimeHandler.hourDuration / turnInAhour - (turnInAhour *
                            (System.currentTimeMillis() - TimeHandler.startTime)) % TimeHandler.hourDuration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tready.start();
    }
}
