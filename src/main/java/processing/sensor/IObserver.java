package processing.sensor;

public interface IObserver {
    void update(IObservable observable, Object arg);
}
