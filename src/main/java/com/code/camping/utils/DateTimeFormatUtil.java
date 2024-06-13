package com.code.camping.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatUtil {

    private static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmm");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public static String formatDate(String datetime) throws ParseException {
        Date date = inputFormat.parse(datetime);
        return dateFormat.format(date);
    }

    public static String formatTime(String datetime) throws ParseException {
        Date date = inputFormat.parse(datetime);
        return timeFormat.format(date);
    }
}
