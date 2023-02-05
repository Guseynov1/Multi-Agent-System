package lab.lab1;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static lab.lab1.NumberIdentifier.isArabicNumber;
import static lab.lab1.NumberIdentifier.isRomanNumber;


public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Enter the expression:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        char[] operationCharacter = new char[] {'+', '-', '*', '/'};
        AtomicInteger characterIndex = new AtomicInteger(-1);
        IntStream.range(0, 4).forEach(index -> {
            int intermediateIndex = input.indexOf(operationCharacter[index]);
            if(intermediateIndex > characterIndex.get()) characterIndex.set(intermediateIndex);
        });

        String firstNumber_str = input.substring(0, characterIndex.get());
        String secondNumber_str = input.substring(characterIndex.get() + 1);
        char character = input.charAt(characterIndex.get());

        boolean firstNumberIsRoman_bool = isRomanNumber(firstNumber_str);
        boolean secondNumberIsRoman_bool = isRomanNumber(secondNumber_str);
        boolean firstNumberIsArabic_bool = isArabicNumber(firstNumber_str);
        boolean secondNumberIsArabic_bool = isArabicNumber(secondNumber_str);

        ArabicCalc arabicOperation = new ArabicCalc();
        RomanCalc romanOperation = new RomanCalc();
        String result = "Null";

        if(firstNumberIsArabic_bool & secondNumberIsArabic_bool) {
            switch (character) {
                case '+' -> result = arabicOperation.addition(firstNumber_str, secondNumber_str);
                case '-' -> result = arabicOperation.subtraction(firstNumber_str, secondNumber_str);
                case '*' -> result = arabicOperation.multiplication(firstNumber_str, secondNumber_str);
                case '/' -> result = arabicOperation.division(firstNumber_str, secondNumber_str);
            }
        }
        else if(firstNumberIsRoman_bool & secondNumberIsRoman_bool) {
            switch (character) {
                case '+' -> result = romanOperation.addition(firstNumber_str, secondNumber_str);
                case '-' -> result = romanOperation.subtraction(firstNumber_str, secondNumber_str);
                case '*' -> result = romanOperation.multiplication(firstNumber_str, secondNumber_str);
                case '/' -> result = romanOperation.division(firstNumber_str, secondNumber_str);
            }
        }
        else {
            System.out.println("Invalid number format. Try again!");
            System.exit(0);
        }
        System.out.println(result);
    }
}
