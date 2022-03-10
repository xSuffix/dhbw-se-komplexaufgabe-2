package processing.centrifuge;

import sensor.IObservable;
import sensor.IObserver;

import java.util.HashSet;
import java.util.Set;

public class Centrifuge implements IObservable {
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
    public void update(IObservable observable, Object arg) {

    }

    public void insert(String matter) {
        toProcess.append(matter);
    }

    public void turnOn() {

    }

    public void turnOff() {

    }
}
