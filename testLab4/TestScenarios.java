package testLab4;

import lab4.Behaviour.Distributer.ReceiveTaskToAuction;
import lab4.Behaviour.Producer.ReceiveTask;
import testLab4.TestBeh.ConsumerFSMTest;
import testLab4.TestBeh.FSMTestСЭС;
import testLab4.TestBeh.FSMTestТЭС;
import testLab4.TestBeh.FSMTestВЭС;
import lab4.Config.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class TestScenarios extends TestUtil {
    Data producerData = new Data();
    Data consumerData = new Data();
    @Test
    public void firstScenario() throws InterruptedException {
        List<String> services = new ArrayList<>();
        services.add("jade.core.messaging.TopicManagementService");
        services.add("jade.core.event.NotificationService");

        startJade(services);
        ConsumerFSMTest behaviourToTest = new ConsumerFSMTest(consumerData, 12, 50);
        CreateAgent("residentialBuildingConsumer", behaviourToTest);
        CreateAgent("ТЭС",new FSMTestТЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("ВЭС",new FSMTestВЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("firstDistributer", new ReceiveTaskToAuction());
        sleep(35000);

        Assertions.assertEquals(2, behaviourToTest.onEnd());
    }
    @Test
    public void secondScenario() throws InterruptedException {
        List<String> services = new ArrayList<>();
        services.add("jade.core.messaging.TopicManagementService");
        services.add("jade.core.event.NotificationService");

        startJade(services);
        ConsumerFSMTest behaviourToTest = new ConsumerFSMTest(consumerData, 9, 100);
        CreateAgent("residentialBuildingConsumer", behaviourToTest);
        CreateAgent("ТЭС",new FSMTestТЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("СЭС",new FSMTestСЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("ВЭС",new FSMTestВЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("firstDistributer", new ReceiveTaskToAuction());
        sleep(35000);

        Assertions.assertEquals(1, behaviourToTest.onEnd());
    }
    @Test
    public void thirdScenario() throws InterruptedException {
        List<String> services = new ArrayList<>();
        services.add("jade.core.messaging.TopicManagementService");
        services.add("jade.core.event.NotificationService");

        startJade(services);
        ConsumerFSMTest behaviourToTest = new ConsumerFSMTest(consumerData,18, 100);
        CreateAgent("residentialBuildingConsumer", behaviourToTest);
        CreateAgent("ТЭС",new FSMTestТЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("СЭС",new FSMTestСЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("ВЭС",new FSMTestВЭС(producerData), new ReceiveTask(producerData));
        CreateAgent("firstDistributer", new ReceiveTaskToAuction());
        sleep(50000);

        Assertions.assertEquals(4, behaviourToTest.onEnd());
    }
}
