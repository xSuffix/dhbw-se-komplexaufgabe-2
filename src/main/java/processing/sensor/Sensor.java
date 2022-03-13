package processing.sensor;

import java.util.List;

public record Sensor(List<IObserver> observers) implements IObserver {

    @Override
    public void update(IObservable observable, Object arg) {
        for (IObserver observer : observers) {
            if (observer != null) observer.update(observable, arg);
        }
    }
}
