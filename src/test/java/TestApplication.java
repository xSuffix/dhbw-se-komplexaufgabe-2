import main.Configuration;
import main.Utility;
import material.AcidStorage;
import org.junit.jupiter.api.*;
import processing.centrifuge.Centrifuge;
import processing.filter.IFilter;
import processing.pl.PL;
import processing.pl.PL01;
import processing.pl.PL02;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class TestApplication {
    private final String context = Configuration.INSTANCE.testContext;
    private AcidStorage acidStorage;
    private Centrifuge centrifuge;

    @BeforeEach
    public void setup() {
        acidStorage = new AcidStorage();
        centrifuge = new Centrifuge();
    }

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

        PL pl = new PL01(new PL02());
        pl.process(matterContainingGold, acidStorage, centrifuge);
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

    }

    @Test
    @Order(3)
    public void testObserver() {

    }
}
