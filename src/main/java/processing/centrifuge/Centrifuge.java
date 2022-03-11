package processing.centrifuge;

import sensor.IObservable;
import sensor.IObserver;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Centrifuge implements IObservable {
    private final ScheduledExecutorService controller = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> processTask;
    private final StringBuilder toProcess = new StringBuilder();
    private final Set<IObserver> observers = new HashSet<>();

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
            toProcess.append(matter);
            notifyObservers(true);
        }
    }

    public void turnOn() {
        if (processTask == null) {
            System.out.println("[Centrifuge] Turning on");
            processTask = controller.scheduleAtFixedRate(this::process, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void turnOff() {
        if (processTask != null) {
            System.out.println("[Centrifuge] Turning off");
            processTask.cancel(false);
            processTask = null;
        }
    }

    public void process() {

    }
}
