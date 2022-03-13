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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Centrifuge implements IObservable, IObserver {
    private final Configuration config = Configuration.INSTANCE;
    private final String context = config.centrifugeContext;

    private final Set<IObserver> observers = new HashSet<>();

    private final StringBuilder toProcess = new StringBuilder();
    private final IFilter stoneFilter = new FilterStone();
    private final IFilter trashFilter = new FilterTrash();
    private final IFilter goldFilter = new FilterGold();
    private ScheduledExecutorService controller;
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

    @Override
    public void update(IObservable observable, Object arg) {
        if (Objects.equals(arg, "start")) {
            if (config.autoOn) start();
        } else if (Objects.equals(arg, "stop")) {
            if (config.autoOff) stop();
        }
    }

    public void turnOn() {
        Utility.logInfo(context, "Turning on");
        controller = Executors.newScheduledThreadPool(2);
    }

    public void insert(String matter) {
        if (matter != null && !matter.isEmpty()) {
            if (toProcess.isEmpty()) notifyObservers("filled");
            toProcess.append(matter);
        }
    }

    public void start() {
        if (controller == null) turnOn();
        if (processTask == null || processTask.isDone()) {
            Utility.logInfo(context, "Starting");
            processTask = controller.scheduleAtFixedRate(this::process, 0, config.centrifugeMsPerIteration, TimeUnit.MILLISECONDS);
        } else {
            Utility.logError(context, "Cannot start Centrifuge because it is already running");
        }
    }

    public void process() {
        int amount = Math.min(Configuration.INSTANCE.centrifugeAtomsPerIteration, toProcess.length());
        if (amount > 0) {
            String atoms = toProcess.substring(0, amount);
            toProcess.delete(0, amount);
            Utility.logInfo(context, String.format("Processing %s", Utility.analyseMatter(atoms)));

            String noStone = stoneFilter.apply(atoms);
            String noTrash = trashFilter.apply(noStone);
            String noGold = goldFilter.apply(noTrash);
            if (!noGold.isEmpty())
                Utility.logError(context, String.format("Cannot process %s", Utility.analyseMatter(noGold)));
        } else {
            notifyObservers("empty");
        }
    }

    public void stop() {
        if (processTask != null && !processTask.isDone()) {
            Utility.logInfo(context, "Stopping");
            processTask.cancel(false);
            notifyObservers("stopped");
        } else {
            Utility.logError(context, "Cannot stop Centrifuge because it isn't running");
        }
    }

    public IFilter[] getFilters() {
        return new IFilter[]{
                stoneFilter,
                trashFilter,
                goldFilter
        };
    }

    public void turnOff() {
        Utility.logInfo(context, "Turning off");
        controller.shutdown();
    }
}
