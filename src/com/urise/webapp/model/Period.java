package com.urise.webapp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Period {
    private Calendar start;
    private Calendar end;

    public Period(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
    }

    public Period() {
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(start, period.start) && Objects.equals(end, period.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return String.format("%s  -  %s", format.format(start.getTime()), format.format(end.getTime()));
    }
}
