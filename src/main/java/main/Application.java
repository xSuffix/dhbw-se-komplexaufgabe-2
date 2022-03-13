package main;

import material.AcidStorage;
import material.Block;
import material.BlockStorage;
import processing.centrifuge.Centrifuge;
import processing.filter.IFilter;
import processing.pl.PL01;
import processing.pl.PL02;
import processing.sensor.Sensor;

import java.util.Arrays;

public class Application {
    private static final Configuration config = Configuration.INSTANCE;
    private static final String context = config.testContext;

    private static final AcidStorage acidStorage = new AcidStorage();
    private static final BlockStorage storage = new BlockStorage();
    private static final Centrifuge centrifuge = new Centrifuge();
    private static final PL01 productionLine = new PL01(new PL02());


    public static void main(String... args) {
        int numberOfAtoms = config.storageSize * config.blockSize[0] * config.blockSize[1] * config.blockSize[2];
        int timeInSeconds = (int) (((double) numberOfAtoms / config.centrifugeAtomsPerStack) * ((double) config.centrifugeMsPerStack / 1000));
        Utility.logInfo(context, String.format("BlockStorage(%d), Block(%s)", config.storageSize, Arrays.toString(config.blockSize)));
        Utility.logInfo(context, String.format("Processing %s atoms, ETA: %s seconds", Utility.formatNumber(numberOfAtoms), Utility.formatNumber(timeInSeconds)));

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
            System.out.println("");

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
        System.out.println("");
        StringBuilder filtered = new StringBuilder();
        for (IFilter filter : centrifuge.getFilters()) filtered.append(filter.takeFiltered());
        Utility.logInfo(context, String.format("Done! Processed %s", Utility.analyseMatter(filtered.toString())));
    }
}
