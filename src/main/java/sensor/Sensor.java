package sensor;

import processing.centrifuge.Centrifuge;

public class Sensor {
    private Centrifuge centrifuge;

    public void watch(Centrifuge centrifuge) {
        this.centrifuge = centrifuge;
    }

    public void detectMatterAdded() {
        centrifuge.turnOn();
    }


}
