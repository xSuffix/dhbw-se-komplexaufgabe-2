package main;

import material.AcidStorage;
import material.Block;
import material.BlockStorage;
import processing.centrifuge.Centrifuge;
import processing.filter.IFilter;
import processing.pl.PL;
import processing.sensor.IObservable;
import processing.sensor.IObserver;
import processing.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Worker implements IObserver {
    private final Configuration config = Configuration.INSTANCE;

    private final AcidStorage acidStorage;
    private final BlockStorage blockStorage;
    private final PL productionLine;
    private final Centrifuge centrifuge;

    public Worker(AcidStorage acidStorage, BlockStorage blockStorage, PL productionLine, Centrifuge centrifuge) {
        this.acidStorage = acidStorage;
        this.blockStorage = blockStorage;
        this.productionLine = productionLine;
        this.centrifuge = centrifuge;

        List<IObserver> sensorObservers = new ArrayList<>();
        sensorObservers.add(centrifuge);
        sensorObservers.add(this);
        centrifuge.registerObserver(new Sensor(sensorObservers));
    }

    public void fillAll() {
        acidStorage.fillWithMagic();
        blockStorage.fillWithMagic();
    }

    public void processBlocks() {
        if (blockStorage.countBlocks() > 0) {
            System.out.println("");

            for (Block b : blockStorage.takeBlocks(config.blocksPerIteration))
                productionLine.process(b.addChemicalSubstance(acidStorage.takeAcidForBlock()), acidStorage, centrifuge);

            for (IFilter filter : productionLine.getAcidFilters())
                acidStorage.fill(filter.takeFiltered());

            if (!config.autoOn) centrifuge.start();
        } else {
            analyzeFilterContainers();
        }
    }

    @Override
    public void update(IObservable observable, Object arg) {
        // arg = (true: centrifuge was empty and got filled, false: centrifuge was filled and is empty)
        if (!Objects.equals(arg, true)) processBlocks();
    }

    public void analyzeFilterContainers() {
        System.out.println("");
        centrifuge.turnOff();
        StringBuilder filtered = new StringBuilder();
        for (IFilter filter : centrifuge.getFilters()) filtered.append(filter.takeFiltered());
        String context = config.testContext;
        Utility.logInfo(context, String.format("Done! Processed %s", Utility.analyseMatter(filtered.toString())));
    }
}
