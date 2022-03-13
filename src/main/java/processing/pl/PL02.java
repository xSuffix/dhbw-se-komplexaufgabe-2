package processing.pl;

import material.AcidStorage;
import processing.centrifuge.Centrifuge;
import processing.filter.FilterAcid;
import processing.filter.IFilter;

import java.util.ArrayList;
import java.util.List;

public class PL02 extends PL {
    private final IFilter acidFilter = new FilterAcid();

    public PL02() {
        setAcidStorage(super.getAcidStorage());
        setCentrifuge(super.getCentrifuge());
    }

    @Override
    public void process(String matter, AcidStorage acidStorage, Centrifuge centrifuge) {
        if (containsGold(matter)) centrifuge.insert(acidFilter.apply(matter));
        else super.process(matter, acidStorage, centrifuge);
    }

    @Override
    public List<IFilter> getAcidFilters() {
        List<IFilter> filters = new ArrayList<>();
        filters.add(acidFilter);
        return filters;
    }
}
