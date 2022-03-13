import main.Configuration;
import main.Utility;
import main.Worker;
import material.AcidStorage;
import material.BlockStorage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import processing.centrifuge.Centrifuge;
import processing.filter.*;
import processing.pl.PL;
import processing.pl.PL01;
import processing.pl.PL02;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestApplication {
    private final Configuration config = Configuration.INSTANCE;
    private final String context = config.testContext;

    /**
     * Check if PL01 correctly passes matter with gold to its successor
     * This is tested by processing acid matter with and without gold
     * and then checking if both acid filters contain acid
     */
    @Test
    @Order(1)
    public void testCOR() {
        String matterContainingGold = "KAABCAAGCA";
        String matterNotContainingGold = matterContainingGold.replace("G", "K");
        assertTrue(matterContainingGold.contains("G"));

        AcidStorage acidStorage = new AcidStorage();
        Centrifuge centrifuge = new Centrifuge();


        PL pl = new PL01(new PL02());
        Utility.logInfo(context, String.format("Processing matter with gold: %s", Utility.analyseMatter(matterContainingGold)));
        pl.process(matterContainingGold, acidStorage, centrifuge);
        Utility.logInfo(context, String.format("Processing matter without gold: %s", Utility.analyseMatter(matterNotContainingGold)));
        pl.process(matterNotContainingGold, acidStorage, centrifuge);

        List<IFilter> acidFilters = pl.getAcidFilters();
        assertTrue(acidFilters.size() > 1);
        for (IFilter filter : acidFilters) {
            String content = filter.takeFiltered();
            assertTrue(content.length() > 0);
            Utility.logInfo(context, String.format("Filter contains %s", Utility.analyseMatter(content)));
        }
    }

    @Test
    @Order(2)
    public void testFilter() {
        List<IFilter> filters = new ArrayList<>();
        filters.add(new FilterAcid());
        filters.add(new FilterGold());
        filters.add(new FilterTrash());
        filters.add(new FilterStone());

        String matter = "KKAABBCCGGXXE";
        Utility.logInfo(context, String.format("Matter contains %s", Utility.analyseMatter(matter)));

        for (IFilter filter : filters) {
            matter = filter.apply(matter);
            CharSequence filteredAtoms = filter.getSetting();
            Utility.logInfo(context, String.format("After filtering out %s, matter now contains %s", filteredAtoms, Utility.analyseMatter(matter)));
            assertFalse(matter.contains(filteredAtoms));
        }
    }

    @Test
    @Order(3)
    public void testObserver() {
        AcidStorage acidStorage = new AcidStorage();
        BlockStorage blockStorage = new BlockStorage(1);
        PL pl = new PL01(new PL02());
        Centrifuge centrifuge = new Centrifuge();

        Worker sam = new Worker(acidStorage, blockStorage, pl, centrifuge);
        sam.fillAll();

        centrifuge.insert("G");
        assertEquals(0, blockStorage.takeBlocks(1).size());
    }
}
