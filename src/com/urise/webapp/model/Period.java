package com.urise.webapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Period implements Serializable {

    private static final long serialVersionUID = 1L;
    private Calendar start;
    private Calendar end;
    private String title;
    private String description;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(start, period.start) && Objects.equals(end, period.end) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, title, description);
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("   %s  -  %s\n", format.format(start.getTime()), format.format(end.getTime())));
        sb.append(String.format("     %s\n", title));
        if (description != null && !description.isEmpty()) {
            sb.append(String.format("     %s\n", description));
        }
        return sb.toString();
    }
}
