package lab.lab1;

public class NumberIdentifier {
    public static boolean isArabicNumber(String number) {
        return switch (number) {
            case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "40", "50", "90", "100" -> true;
            case "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XL", "L", "XC", "C" -> false;
            default -> throw new IllegalStateException("Unexpected value");
        };
    }

    public static boolean isRomanNumber(String number) {
        return switch (number) {
            case "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XL", "L", "XC", "C" -> true;
            case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "40", "50", "90", "100" -> false;
            default -> throw new IllegalStateException("Unexpected value");
        };
    }




}
