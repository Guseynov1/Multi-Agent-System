package lab4.Config;

import jade.core.AID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@lombok.Data
public class Data {
    private double distributerMaxPrice, distributerLoad;
    private double consumerMaxPrice, consumerLoad;
    private HashMap<AID, Double> producerLoad = new HashMap<>();
    private String winnerMSG;
    private AID topic;
    private int numberOfProducers;
    private List<String> bestsPrices = new ArrayList<>();
    private int onEnd = 2;
    List<PriceWithName> pricesWithNames = new ArrayList<>();
    private double minPrice;

    @lombok.Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PriceWithName {
        private double price;
        private String name;
    }
}

