package lab.lab4other.Master;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import lombok.Data;
import jade.core.Runtime;

import java.util.Properties;

@Data
public class Main {

    public static void main(String[] args) {
        System.out.println("Starting launcher app");
        Properties property = new Properties();
        System.out.println("Parsing config");
//        try (FileInputStream fis = new FileInputStream(args.length > 0 ? args[0] : "config.properties")){
//            property.load(fis);
//            System.out.println("Config OK");
//        } catch (IOException e) {
//            System.err.println("ОШИБКА: файл конфигурации не найден!");
//            return;
//        }

        Runtime runtime = Runtime.instance();
        System.out.println("Connecting to JADE");
        ProfileImpl profile = createProfile(property);
        ContainerController container = runtime.createMainContainer(profile);
        System.out.println("Connected to JADE");


        try {
            AgentController agentController = container.
                    createNewAgent(property.getProperty("agent.name","Smith"),
                    property.getProperty("agent.class","lab.lab4.Master.Launcher"),
                    new Object[] { property.getProperty("xml.filepath", "Meeting.xml") }
            );

            // здесь импортируем в xml файлы из excel
//            importLoad(property.getProperty("xml.filepath", "src/main/resources/config.xml"));
//            importPower(property.getProperty("xml.filepath"));
//            handler(property.getProperty("xml.filepath", "firstConsumer.xml"));
            property.getProperty("xml.filepath", "src/main/resources/Meeting.xml");
            agentController.start();
            System.out.println("Launcher agent has been created");
        } catch (ControllerException e) {
            System.out.println("Невозможно создать агента Launcher: " + e.getMessage());
        }
    }

    private static ProfileImpl createProfile(Properties property) {
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, property.getProperty("agent.container", "Container"));
        profile.setParameter(Profile.MAIN_HOST, property.getProperty("agent.host", "127.0.0.1"));
        profile.setParameter(Profile.MAIN_PORT, property.getProperty("agent.port", "1099"));
        profile.setParameter(Profile.LOCAL_PORT, property.getProperty("agent.local-port", "1201"));

        if (property.getProperty("agent.gui", "false").equals("true")) profile.setParameter("gui", "true");
        if (property.containsKey("agent.services")) profile.setParameter("services", property.getProperty("agent.services", ""));
        if (property.containsKey("agent.mtp")) profile.setParameter("mtps", property.getProperty("agent.mtp",""));
        return profile;
    }
}
