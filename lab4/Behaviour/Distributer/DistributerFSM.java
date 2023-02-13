package lab4.Behaviour.Distributer;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import lab4.Behaviour.Distributer.FSM.*;
import lab4.Behaviour.Distributer.FSM.Division.*;
import lab4.Config.Data;
import lab4.Config.Data.PriceWithName;

public class DistributerFSM extends FSMBehaviour {
    Data pricesForDistributerData = new Data();
    Data data;
    PriceWithName bestPrice = new PriceWithName();
    public DistributerFSM(Agent agent, Data data) {
        super(agent);
        this.data = data;

        registerFirstState(new SendTopicNameForProducer(getAgent(), data), "SendTopic");
        registerState(new SendTaskForTopic(getAgent(), 4000, data), "SendTask");
        registerState(new ParallelAfterDivision.Parallel(getAgent(), pricesForDistributerData, 7000), "ReceivingPrices");
        registerState(new ChoosingBestPrice(getAgent(), data, pricesForDistributerData, bestPrice), "ChooseBestPrice");
        registerState(new WaitingForConfirmParallel(getAgent(),3000), "Wait");
        registerLastState(new IBoughtEnergy(bestPrice), "BoughtEnergy");
        registerLastState(new SellerAlreadySold(getAgent(), data),"Restart");
        registerState(new DivisionContract(getAgent(), data), "DivisionContract");
        registerState(new ParallelAfterDivision(getAgent(), pricesForDistributerData, 5000), "ReceivePriceAfterDiv");
        registerState(new ChoosingBestPricesAfterDivision(getAgent(), data, pricesForDistributerData), "ChooseBestPriceAfterDiv");
        registerLastState(new TheyDontHaveEnergy(), "NoEnergy");
        registerLastState(new MinPriceTooLargeAfterDivision(getAgent(), pricesForDistributerData), "NewMaxPrice");
        registerState(new WaitingForConfirmAfterDivisionParallel(getAgent(), 6000, data), "ConfirmAfterDivision");
        registerLastState(new SendingReport(getAgent(), data), "ReportBought");

        registerDefaultTransition("SendTopic", "SendTask");
        registerDefaultTransition("SendTask","ReceivingPrices");
        registerTransition("ReceivingPrices","ChooseBestPrice", 1);
        registerDefaultTransition("ChooseBestPrice", "Wait");
        registerTransition("Wait", "BoughtEnergy", 1);
        registerTransition("Wait", "Restart", 2);
        registerTransition("ReceivingPrices","DivisionContract", 2);
        registerDefaultTransition("DivisionContract", "ReceivePriceAfterDiv");
        registerTransition("ReceivePriceAfterDiv", "ChooseBestPriceAfterDiv", 1);
        registerTransition("ReceivePriceAfterDiv","NoEnergy", 2);
        registerTransition("ChooseBestPriceAfterDiv","ConfirmAfterDivision", 1);
        registerTransition("ChooseBestPriceAfterDiv","NewMaxPrice", 2);
        registerTransition("ConfirmAfterDivision","ReportBought", 1 );
        registerTransition("ConfirmAfterDivision","NoEnergy", 2 );
    }

    public static class SellerAlreadySold extends OneShotBehaviour {
        Data data;
        public SellerAlreadySold(Agent agent, Data data) {
            super(agent);
            this.data = data;
        }

        @Override
        public void action() {
            getAgent().addBehaviour(new DistributerFSM(getAgent(), data));
        }
    }


}
