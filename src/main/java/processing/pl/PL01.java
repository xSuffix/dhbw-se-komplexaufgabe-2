package processing.pl;

import material.AcidStorage;
import processing.centrifuge.Centrifuge;
import processing.filter.FilterAcid;
import processing.filter.IFilter;

import java.util.ArrayList;
import java.util.List;

public class PL01 extends PL {
    IFilter acidFilter = new FilterAcid();

    public PL01(PL successor) {
        setSuccessor(successor);
    }

    @Override
    public void process(String matter, AcidStorage acidStorage, Centrifuge centrifuge) {
        if (!containsGold(matter)) centrifuge.insert(acidFilter.apply(matter));
        else getSuccessor().process(matter, acidStorage, centrifuge);
    }

    @Override
    public List<IFilter> getAcidFilters() {
        List<IFilter> filters = new ArrayList<>();
        filters.add(acidFilter);
        filters.addAll(getSuccessor().getAcidFilters());
        return filters;
    }
}
