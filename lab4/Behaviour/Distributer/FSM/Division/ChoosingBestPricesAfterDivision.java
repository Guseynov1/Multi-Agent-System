package lab4.Behaviour.Distributer.FSM.Division;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import lab4.Config.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ChoosingBestPricesAfterDivision extends OneShotBehaviour {
    private int onEnd = 2;
    Data data;
    Data priceForDistributerData;
    List<Double> firstProducerPrices = new ArrayList<>(), secondProducerPrices = new ArrayList<>(),
    thirdProducerPrices = new ArrayList<>(), allMinPricesAfterDivision = new ArrayList<>();

    public ChoosingBestPricesAfterDivision(Agent agent, Data data, Data priceForDistributerData) {
        super(agent);
        this.data = data;
        this.priceForDistributerData = priceForDistributerData;
    }

    @Override
    public void action() {
        for (int i = 0; i < priceForDistributerData.getPricesWithNames().size(); i++) {
            switch (priceForDistributerData.getPricesWithNames().get(i).getName()) {
                case "ТЭС" -> firstProducerPrices.add(priceForDistributerData.getPricesWithNames().get(i).getPrice());
                case "ВЭС" -> secondProducerPrices.add(priceForDistributerData.getPricesWithNames().get(i).getPrice());
                case "СЭС" -> thirdProducerPrices.add(priceForDistributerData.getPricesWithNames().get(i).getPrice());
            }
        }
        check(firstProducerPrices, allMinPricesAfterDivision, "ТЭС");
        check(secondProducerPrices, allMinPricesAfterDivision, "ВЭС");
        check(thirdProducerPrices, allMinPricesAfterDivision, "СЭС");
        priceForDistributerData.setMinPrice(allMinPricesAfterDivision.get(0));

        IntStream.range(1, allMinPricesAfterDivision.size()).forEach(i -> {
            if (priceForDistributerData.getMinPrice() <= allMinPricesAfterDivision.get(i)) {
                priceForDistributerData.setMinPrice(allMinPricesAfterDivision.get(i));
            }
        });
    }


    public void check(List<Double> producerPrices, List<Double> allMinPricesAfterDivision, String name) {
        if (producerPrices.size() > 0) {
            double minPrice = producerPrices.get(0);
            for (int i = 1; i < producerPrices.size(); i++) {
                if (minPrice >= producerPrices.get(i)) {
                    minPrice = producerPrices.get(i);
                }
            }
            allMinPricesAfterDivision.add(minPrice);
            if (minPrice <= data.getDistributerMaxPrice()) {
                onEnd = 1;
                ACLMessage win = new ACLMessage(ACLMessage.PROPOSE);
                win.addReceiver(new AID(name, false));
                win.setProtocol("WinnerAfterDiv");
                win.setContent(name + minPrice);
                getAgent().send(win);
            }
        }
    }

    @Override
    public int onEnd() {
        return onEnd;
    }
}
