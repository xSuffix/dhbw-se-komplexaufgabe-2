package material;

import main.Configuration;
import main.Utility;

import java.util.Arrays;

public class AcidStorage {
    private final StringBuilder storage = new StringBuilder();
    private final Configuration config = Configuration.INSTANCE;
    private final String context = config.acidStorageContext;

    public AcidStorage() {
        Utility.logInfo(context, String.format("Initializing | %s acid", storage.length()));
    }

    public void fillWithMagic() {
        int acidNeeded = Math.max(config.acidPerBlock * config.blocksPerIteration - storage.length(), 0);
        fillWithMagic(acidNeeded);
    }

    public void fillWithMagic(int amount) {
        Utility.logInfo(context, String.format("Adding %s acid with magic | Total: %s", amount, storage.length()));
        char[] container = new char[amount];
        Arrays.fill(container, config.acid);
        storage.append(container);
    }

    public String takeAcidForBlock() {
        if (storage.length() >= config.acidPerBlock) {
            String acid = (String) storage.subSequence(0, config.acidPerBlock);
            storage.delete(0, config.acidPerBlock);
            Utility.logInfo(context, String.format("Taking %s acid | Total: %s", config.acidPerBlock, storage.length()));
            return acid;
        } else {
            Utility.logError(context, String.format("Out of acid (%d < %d)", storage.length(), config.acidPerBlock));
        }
        return "";
    }

    public void fill(String acid) {
        if (acid.matches("[K]*")) {
            storage.append(acid);
            Utility.logInfo(context, String.format("Adding %s acid | Total: %s", acid.length(), storage.length()));
        } else {
            Utility.logError(context, String.format("Declining and discarding impure acid: %s", acid));
        }
    }
}
