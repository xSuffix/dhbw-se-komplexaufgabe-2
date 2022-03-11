package sensor;

public interface IObservable {
    void registerObserver(IObserver observer);

    void removeObserver(IObserver observer);

    void notifyObservers(Object arg);
}
