package material;

import main.Configuration;
import main.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BlockStorage {
    private final Block[] storage;
    private final String context = Configuration.INSTANCE.blockStorageContext;


    public BlockStorage() {
        this(Configuration.INSTANCE.numberOfBlocks);
    }

    public BlockStorage(int size) {
        Utility.logInfo(context, String.format("Initializing | %d spaces, 0 filled", size));
        storage = new Block[size];
    }

    public List<Block> takeBlocks(int amount) {
        List<Block> takenBlocks = new ArrayList<>();

        int i = 0;
        int taken = 0;
        while (taken < amount && i < storage.length) {
            if (storage[i] != null) {
                takenBlocks.add(storage[i]);
                storage[i] = null;
                taken++;
            }
            i++;
        }

        Utility.logInfo(context, String.format("Taking %s Blocks | Total: %s", Utility.formatNumber(taken), Utility.formatNumber(countBlocks())));
        return takenBlocks;
    }

    public int countBlocks() {
        return (int) Arrays.stream(storage).filter(Objects::nonNull).count();
    }

    public void fillWithMagic() {
        int amountFilled = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = new Block();
                amountFilled++;
            }
        }
        Utility.logInfo(context, String.format("Adding %s blocks with magic | Total: %s", amountFilled, storage.length));
    }
}
