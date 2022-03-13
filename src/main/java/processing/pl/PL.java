package processing.pl;

import main.Configuration;
import main.Utility;
import material.AcidStorage;
import processing.centrifuge.Centrifuge;
import processing.filter.IFilter;

import java.util.ArrayList;
import java.util.List;

public class PL {
    private PL successor;
    private AcidStorage acidStorage;
    private Centrifuge centrifuge;

    public PL getSuccessor() {
        return successor;
    }

    public void setSuccessor(PL successor) {
        this.successor = successor;
    }

    public AcidStorage getAcidStorage() {
        return acidStorage;
    }

    public void setAcidStorage(AcidStorage acidStorage) {
        this.acidStorage = acidStorage;
    }

    public Centrifuge getCentrifuge() {
        return centrifuge;
    }

    public void setCentrifuge(Centrifuge centrifuge) {
        this.centrifuge = centrifuge;
    }

    protected boolean containsGold(String matter) {
        return matter.contains(String.valueOf(Configuration.INSTANCE.gold));
    }

    public void process(String matter, AcidStorage acidStorage, Centrifuge centrifuge) {
        if (getSuccessor() != null) getSuccessor().process(matter, acidStorage, centrifuge);
        else Utility.logError("PL", String.format("Cannot process %s", matter));
    }

    public List<IFilter> getAcidFilters() {
        return new ArrayList<>();
    }
}
