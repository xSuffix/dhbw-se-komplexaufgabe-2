package processing.pl;

import main.Utility;
import material.AcidStorage;
import processing.centrifuge.Centrifuge;
import processing.filter.*;

import java.util.ArrayList;
import java.util.List;

public class PL02 extends PL {
    public PL02() {
        setAcidStorage(super.getAcidStorage());
        setCentrifuge(super.getCentrifuge());
    }

    private final IFilter acidFilter = new FilterAcid();

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
