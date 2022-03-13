package processing.sensor;

import java.util.HashSet;
import java.util.Set;

public class Sensor implements IObserver, IObservable {
    private final Set<IObserver> subscribers = new HashSet<>();

    @Override
    public void update(IObservable observable, Object arg) {
        notifyObservers(arg);
    }

    @Override
    public void registerObserver(IObserver observer) {
        if (observer != null) subscribers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        if (observer != null) subscribers.remove(observer);
    }

    @Override
    public void notifyObservers(Object arg) {
        String msg = "";
        if (arg.equals("filled")) msg = "start";
        else if (arg.equals("stopped")) msg = "fill";
        else if (arg.equals("empty")) msg = "stop";

        if (!msg.equals("")) {
            for (IObserver subscriber : subscribers) {
                subscriber.update(this, msg);
            }
        }
    }
}
