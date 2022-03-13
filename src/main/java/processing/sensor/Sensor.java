package processing.sensor;

import main.Application;
import processing.centrifuge.Centrifuge;

public class Sensor implements IObserver {

    @Override
    public void update(IObservable observable, Object arg) {
        if (observable instanceof Centrifuge centrifuge) {
            if (arg.equals(true)) centrifuge.turnOn();
            else {
                centrifuge.turnOff();
                Application.processBlocks();
            }
        }
    }
}
