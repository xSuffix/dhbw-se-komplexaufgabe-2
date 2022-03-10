import material.Block;
import processing.pl.PL01;
import processing.pl.PL02;
import storage.Storage;

public class Application {

    public static void main(String... args) {
        Storage storage = new Storage(100);
        PL01 productionLine = new PL01(new PL02());
        storage.fillStorage();
        for (Block b : storage.getBlocks()) productionLine.process(b.addChemicalSubstance("KKKKK"));
    }
}
