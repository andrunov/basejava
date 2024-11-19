package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {

    private String name;
    private String website;
    private List<Period> periods;

    public Company(String name, String website) {
        this.name = name;
        this.website = website;
        this.periods = new ArrayList<>();
    }

    public Company() {
        this.periods = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public void addPeriod(Period period) {
        periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(website, company.website) && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, periods);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s\n", name));
        if (website != null && !website.isEmpty()) {
            sb.append(String.format("  %s\n", website));
        }
        sb.append("\n");
        for (Period period : periods) {
            sb.append(String.format("  %s\n", period));
        }
        return sb.toString();
    }
}
