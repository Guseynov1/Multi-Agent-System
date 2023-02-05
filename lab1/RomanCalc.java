package lab.lab1;

import java.io.IOException;

public class RomanCalc extends Calculations {
    @Override
    public String addition(String numberOne, String numberTwo) throws IOException {
        Operation execute = Integer::sum;
        return convertToRomanNumber(execute.execute(convertToArabicNumber(numberOne), convertToArabicNumber(numberTwo)));
    }

    @Override
    public String subtraction(String numberOne, String numberTwo) throws IOException {
        Operation execute = (first, second) -> first - second;
        return convertToRomanNumber(execute.execute(convertToArabicNumber(numberOne), convertToArabicNumber(numberTwo)));

    }

    @Override
    public String multiplication(String numberOne, String numberTwo) throws IOException {
        Operation execute = (first, second) -> first * second;
        return convertToRomanNumber(execute.execute(convertToArabicNumber(numberOne), convertToArabicNumber(numberTwo)));
    }

    @Override
    public String division(String numberOne, String numberTwo) throws IOException {
        Operation execute = (first, second) -> (first / second);
        return convertToRomanNumber(execute.execute(convertToArabicNumber(numberOne), convertToArabicNumber(numberTwo)));
    }

    public int convertToArabicNumber(String romanNumber) throws IOException {
        int arabicNumber;
        switch (romanNumber) {
            case "I"    -> arabicNumber = 1;
            case "II"   -> arabicNumber = 2;
            case "III"  -> arabicNumber = 3;
            case "IV"   -> arabicNumber = 4;
            case "V"    -> arabicNumber = 5;
            case "VI"   -> arabicNumber = 6;
            case "VII"  -> arabicNumber = 7;
            case "VIII" -> arabicNumber = 8;
            case "IX"   -> arabicNumber = 9;
            case "X"    -> arabicNumber = 10;
            case "XL"   -> arabicNumber = 40;
            case "L"    -> arabicNumber = 50;
            case "XC"   -> arabicNumber = 90;
            case "C"    -> arabicNumber = 100;
            default     -> throw new IOException("Invalid input data format");
        }
        return arabicNumber;
    }


    private String convertToRomanNumber(int result) {
        int[] arabic = new int[]{ 100, 90, 50, 40, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        String[] roman = new String[]{ "C", "XC", "L", "XL", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I" };
        if(result < 0) throw new Error("Not a positive result");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arabic.length; i++) {
            while (result >= arabic[i]){
                result -= arabic[i];
                sb.append(roman[i]);
            }
        }
        return sb.toString();
    }

}
