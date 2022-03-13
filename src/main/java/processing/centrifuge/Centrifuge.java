package processing.centrifuge;

import main.Configuration;
import main.Utility;
import processing.filter.FilterGold;
import processing.filter.FilterStone;
import processing.filter.FilterTrash;
import processing.filter.IFilter;
import processing.sensor.IObservable;
import processing.sensor.IObserver;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Centrifuge implements IObservable {
    private final Configuration config = Configuration.INSTANCE;
    private final String context = config.centrifugeContext;

    private final Set<IObserver> observers = new HashSet<>();

    private final ScheduledExecutorService controller = Executors.newScheduledThreadPool(1);
    private final StringBuilder toProcess = new StringBuilder();
    private final IFilter stoneFilter = new FilterStone();
    private final IFilter trashFilter = new FilterTrash();
    private final IFilter goldFilter = new FilterGold();
    private ScheduledFuture<?> processTask;

    @Override
    public void registerObserver(IObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(IObserver observer) {
        if (observer != null) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        for (IObserver observer : observers) {
            observer.update(this, arg);
        }
    }

    public void insert(String matter) {
        if (matter != null && !matter.isEmpty()) {
            if (toProcess.isEmpty() && config.autoOn) notifyObservers(true);
            toProcess.append(matter);
        }
    }

    public void turnOn() {
        if (processTask == null || processTask.isDone()) {
            Utility.logInfo(context, "Turning on");
            processTask = controller.scheduleAtFixedRate(this::process, 0, config.centrifugeMsPerStack, TimeUnit.MILLISECONDS);
        }
    }

    public void turnOff() {
        if (processTask != null && !processTask.isDone()) {
            Utility.logInfo(context, "Turning off");
            processTask.cancel(false);
        }
    }

    public void process() {
        int amount = Math.min(Configuration.INSTANCE.centrifugeAtomsPerStack, toProcess.length());
        if (amount > 0) {
            String atoms = toProcess.substring(0, amount);
            toProcess.delete(0, amount);
            Utility.logInfo(context, String.format("Processing %s", Utility.analyseMatter(atoms)));

            String noStone = stoneFilter.apply(atoms);
            String noTrash = trashFilter.apply(noStone);
            String noGold = goldFilter.apply(noTrash);
            if (!noGold.isEmpty())
                Utility.logError(context, String.format("Cannot process %s", Utility.analyseMatter(noGold)));
        } else if (config.autoOff) {
            notifyObservers(false);
        }
    }

    public IFilter[] getFilters() {
        return new IFilter[]{
                stoneFilter,
                trashFilter,
                goldFilter
        };
    }
}
