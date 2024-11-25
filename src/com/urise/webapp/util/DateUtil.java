package com.urise.webapp.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {
    public static Calendar of(int year, int month) {
        return new GregorianCalendar(year, month, 1);
    }
}
