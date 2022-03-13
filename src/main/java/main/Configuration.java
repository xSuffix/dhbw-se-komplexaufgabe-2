package main;

public enum Configuration {
    INSTANCE;

    // Storage
    /*  Set to lower number if you don't want to wait > 5 hours or get OutOfMemoryError

        According to specification: 1000000
        Recommended by author: 10000

        I could have generated the blocks JIT without any issues but that wouldn't be 'good' enough
        for someone who wants a battery to be implemented as a 3d array, right?
    */
    public final int numberOfBlocks = 10000;

    // Blocks
    public final int[] blockSize = new int[]{10, 10, 10};
    public final int acidPerBlock = 5;
    public final int blocksPerIteration = 1000;

    // Centrifuge
    public final int centrifugeAtomsPerIteration = 50000;
    public final int centrifugeMsPerIteration = 1000;
    public final boolean autoOn = false;
    public final boolean autoOff = true;

    // Atoms
    public final char acid = 'K';
    public final char stone = 'A';
    public final char gravel = 'B';
    public final char dirt = 'C';
    public final char gold = 'G';
    public final char trash = 'X';

    // Logs
    public final boolean alwaysLogErrors = true;

    public final boolean logTests = true;
    public final boolean logAcidStorage = false;
    public final boolean logBlockStorage = true;
    public final boolean logCentrifuge = true;
    public final boolean logSensor = true;

    public final String testContext = "Test";
    public final String acidStorageContext = "AcidStorage";
    public final String blockStorageContext = "BlockStorage";
    public final String centrifugeContext = "Centrifuge";
    public final String sensorContext = "Sensor";
}
