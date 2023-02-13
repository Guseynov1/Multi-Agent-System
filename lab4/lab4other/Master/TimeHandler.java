package lab.lab4other.Master;

/**
 *  Класс-реализация методов обращения к виртуальному времени
 */
public class TimeHandler {
    public static long startTime = System.currentTimeMillis();
    public static long hourDuration = 120_000;

    public static long getCurrentHour(){
        return (System.currentTimeMillis() - startTime) / hourDuration % 24;
    }

    public static long calcMillisTillNextHour(){
        return hourDuration - (System.currentTimeMillis() - startTime) % hourDuration;
    }
}
