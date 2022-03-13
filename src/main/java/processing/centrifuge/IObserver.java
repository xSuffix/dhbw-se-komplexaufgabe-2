package processing.centrifuge;

public interface IObserver {
    void update(IObservable observable, Object arg);
}
