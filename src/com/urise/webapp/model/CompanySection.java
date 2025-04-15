package com.urise.webapp.model;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CompanySection implements Section<List<Company>> {

    private static final long serialVersionUID = 1L;
    private List<Company> value;

    public CompanySection(List<Company> value) {
        this.value = value;
    }

    @Override
    public List<Company> getValue() {
        return value;
    }

    @Override
    public void setValue(List<Company> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        value.sort((o1, o2) -> {
            Period period1 = null;
            Period period2 = null;
            Optional<Period> optional1 = o1.getPeriods().stream().max(Comparator.comparing(Period::getEnd));
            Optional<Period> optional2 = o2.getPeriods().stream().max(Comparator.comparing(Period::getEnd));
            if (optional1.isPresent()) {
                period1 = optional1.get();
            }
            if (optional2.isPresent()) {
                period2 = optional2.get();
            }
            if (period1 != null && period2 != null) {
                return period2.getStart().compareTo(period1.getStart());
            } else return 0;
        });
        StringBuilder sb = new StringBuilder();
        for (Object val : value) {
            sb.append(String.format("- %s\n", val));
        }
        return sb.toString();
    }
}
