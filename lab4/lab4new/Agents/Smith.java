package lab.lab4new.Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import lab.lab4new.Auto.SmithBeh;
import lombok.SneakyThrows;

import java.util.stream.IntStream;

public class Smith extends Agent {

    @Override
    @SneakyThrows
    protected void setup() {

        Thread.sleep(300);
        System.out.println("\nSmith начал");
        this.addBehaviour(new SmithBeh(this,5000,
                getYellowPages(this, "Producer"),
                getYellowPages(this, "Consumer")));
    }

    /**
     * Метод для регистрации в yellow pages.
     * Агент производитель будет предоставлять сервис Producer
     * поиск производителя осуществляется по сервису Producer
     */
    @SneakyThrows
    public static String[] getYellowPages(Agent agent, String type) {
        DFAgentDescription dfad = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);
        dfad.addServices(sd);
        DFAgentDescription[] result = DFService.search(agent, dfad);
        String[] service = new String[result.length];
        IntStream.range(0, result.length).forEach(index -> service[index] = result[index].getName().getLocalName());
        return service;
    }

    public static void registration(String type, AID name, String local, Agent agent) {
        DFAgentDescription dfad = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        dfad.setName(name);
        sd.setName(local);
        sd.setType(type);
        dfad.addServices(sd);
        try {
            DFService.register(agent, dfad);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

}