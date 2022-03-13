package main;

import jdk.jshell.execution.Util;
import material.AcidStorage;
import material.Block;
import processing.centrifuge.Centrifuge;
import processing.filter.IFilter;
import processing.pl.PL01;
import processing.pl.PL02;
import processing.sensor.Sensor;
import material.BlockStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    private static final Configuration config = Configuration.INSTANCE;
    private static final String context = config.testContext;

    private static final AcidStorage acidStorage = new AcidStorage();
    private static final BlockStorage storage = new BlockStorage();
    private static final Centrifuge centrifuge = new Centrifuge();
    private static final PL01 productionLine = new PL01(new PL02());


    public static void main(String... args) {
        int numberOfAtoms = config.storageSize * config.blockSize[0] * config.blockSize[1] * config.blockSize[2];
        Utility.logInfo(context, String.format("BlockStorage(%d), Block(%s)", config.storageSize, Arrays.toString(config.blockSize)));
        Utility.logInfo(context, String.format("Processing %d atoms, ETA: %d seconds", numberOfAtoms, numberOfAtoms / config.atomsPerSecond));

        fillAll();
        centrifuge.registerObserver(new Sensor());

        processBlocks();
    }

    public static void fillAll() {
        acidStorage.fillWithMagic();
        storage.fillWithMagic();
    }

    public static void processBlocks() {
        if (storage.countBlocks() > 0) {
            for (Block b : storage.takeBlocks(config.blocksPerIteration))
                productionLine.process(b.addChemicalSubstance(acidStorage.takeAcidForBlock()), acidStorage, centrifuge);

            for (IFilter filter : productionLine.getAcidFilters())
                acidStorage.fill(filter.takeFiltered());

            if (!config.autoOn) centrifuge.turnOn();
        } else {
            analyzeFilterContainers();
        }
    }

    public static void analyzeFilterContainers() {
        StringBuilder filtered = new StringBuilder();
        for (IFilter filter : centrifuge.getFilters()) filtered.append(filter.takeFiltered());
        Utility.logInfo(context, String.format("Done! Processed %s", Utility.analyseMatter(filtered.toString())));
    }
}
