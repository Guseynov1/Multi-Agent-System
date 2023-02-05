package lab.lab1;

public class ArabicCalc extends Calculations {
    @Override
    public String addition(String numberOne, String numberTwo) {
        Operation execute = Integer::sum;
        return String.valueOf(execute.execute(Integer.parseInt(numberOne), Integer.parseInt(numberTwo)));
    }

    @Override
    public String subtraction(String numberOne, String numberTwo) {
        Operation execute = ((first, second) -> first - second);
        return String.valueOf(execute.execute(Integer.parseInt(numberOne), Integer.parseInt(numberTwo)));
    }

    @Override
    public String multiplication(String numberOne, String numberTwo) {
        Operation execute = ((first, second) -> first * second);
        return String.valueOf(execute.execute(Integer.parseInt(numberOne), Integer.parseInt(numberTwo)));
    }

    @Override
    public String division(String numberOne, String numberTwo) {
        Operation execute = ((first, second) -> ((int) Math.ceil(first * 1.0 / second)));
        return String.valueOf((execute.execute(Integer.parseInt(numberOne), Integer.parseInt(numberTwo))));

    }


}
