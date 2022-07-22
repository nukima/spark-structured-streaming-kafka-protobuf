package utils;

import java.text.SimpleDateFormat;

public class TimeConverter {
    public static String convertUnixTimeToYear(long unixTime) {
        long unixTimeStamp = unixTime * 1000L;
        java.util.Date date = new java.util.Date(unixTimeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }
    public static String convertUnixTimeToDay(long unixTime) {
        long unixTimeStamp = unixTime * 1000L;
        java.util.Date date = new java.util.Date(unixTimeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(date);
    }
    public static String convertUnixTimeToHour(long unixTime) {
        long unixTimeStamp = unixTime * 1000L;
        java.util.Date date = new java.util.Date(unixTimeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(date);
    }

    public static String convertUnixTimeToDatetime(long unixTime) {
        long unixTimeStamp = unixTime * 1000L;
        java.util.Date date = new java.util.Date(unixTimeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
