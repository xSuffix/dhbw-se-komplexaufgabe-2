package material;

import main.Configuration;

import java.util.Random;

public class Block {
    private final char[][][] matter;
    private final Random random = new Random();
    Configuration config = Configuration.INSTANCE;

    public Block() {
        this(Configuration.INSTANCE.blockSize[0], Configuration.INSTANCE.blockSize[1], Configuration.INSTANCE.blockSize[2]);
    }

    public Block(int sizeX, int sizeY, int sizeZ) {
        matter = new char[sizeX][sizeY][sizeZ];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                for (int k = 0; k < sizeZ; k++) {
                    matter[i][j][k] = generateAtom();
                }
            }
        }
    }

    private char generateAtom() {
        int r = random.nextInt(100) + 1;
        return r <= 40 ? config.stone : r <= 70 ? config.gravel : r <= 90 ? config.dirt : r <= 99 ? config.trash : config.gold;
    }

    public String addChemicalSubstance(String substance) {
        StringBuilder builder = new StringBuilder();
        for (char[][] matter2D : matter) {
            for (char[] matter1D : matter2D) {
                for (char atom : matter1D) {
                    builder.insert(random.nextInt(builder.length() + 1), atom);
                }
            }
        }
        for (char s : substance.toCharArray()) builder.insert(random.nextInt(builder.length() + 1), s);
        return builder.toString();
    }
}
