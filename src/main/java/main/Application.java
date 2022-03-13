package main;

import material.AcidStorage;
import material.BlockStorage;
import processing.centrifuge.Centrifuge;
import processing.pl.PL01;
import processing.pl.PL02;
import processing.sensor.Sensor;

import java.util.Arrays;

public class Application {
    private static final Configuration config = Configuration.INSTANCE;
    private static final String context = config.testContext;

    public static void main(String... args) {
        int atomsPerBlock = config.blockSize[0] * config.blockSize[1] * config.blockSize[2];
        int numberOfAtoms = config.numberOfBlocks * atomsPerBlock;
        double secondsPerIteration = config.centrifugeMsPerIteration / 1000.0;
        double numberOfIterations = numberOfAtoms / (double) Math.min(config.centrifugeAtomsPerIteration, atomsPerBlock * config.blocksPerIteration);
        int timeInSeconds = (int) (numberOfIterations * secondsPerIteration);

        Utility.logInfo(context, String.format("BlockStorage(%d), Block(%s)", config.numberOfBlocks, Arrays.toString(config.blockSize)));
        Utility.logInfo(context, String.format("Processing %s atoms, ETA: %s seconds", Utility.formatNumber(numberOfAtoms), Utility.formatNumber(timeInSeconds)));

        Sensor sensor = new Sensor();
        Centrifuge centrifuge = new Centrifuge();
        sensor.registerObserver(centrifuge);
        centrifuge.registerObserver(sensor);

        Worker sam = new Worker(new AcidStorage(), new BlockStorage(), new PL01(new PL02()), centrifuge);
        sensor.registerObserver(sam);

        sam.fillAll();
        sam.processBlocks();
    }
}
