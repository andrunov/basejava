package com.urise.webapp.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarAdapter extends XmlAdapter<String, Calendar> {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    public Calendar unmarshal(String v) throws Exception {
        Calendar result = Calendar.getInstance();
        result.setTime(FORMATTER.parse(v));
        return result;
    }

    @Override
    public String marshal(Calendar v) throws Exception {
        return FORMATTER.format(v.getTime());
    }
}
