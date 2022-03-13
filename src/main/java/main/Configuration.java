package main;

public enum Configuration {
    INSTANCE;

    // Storage
    public final int storageSize = 10000;

    // Blocks
    public final int[] blockSize = new int[]{10, 10, 10};
    public final int acidPerBlock = 5;
    public final int blocksPerIteration = 10;

    // Centrifuge
    public final int atomsPerSecond = 5000;
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

    public final String testContext = "Test";
    public final String acidStorageContext = "AcidStorage";
    public final String blockStorageContext = "BlockStorage";
    public final String centrifugeContext = "Centrifuge";
}
