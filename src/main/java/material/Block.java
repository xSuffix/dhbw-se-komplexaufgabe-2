package material;

import java.util.Random;

public class Block {
    private final char[][][] matter = new char[10][10][10];
    private final Random random = new Random();

    public Block() {
        for (int i = 0; i < matter.length; i++) {
            for (int j = 0; j < matter[0].length; j++) {
                for (int k = 0; k < matter[0][0].length; k++) {
                    int n = random.nextInt(100) + 1;
                    matter[i][j][k] = n <= 40 ? 'A' : n <= 70 ? 'B' : n <= 90 ? 'C' : n <= 99 ? 'X' : 'G';
                }
            }
        }
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
        for (Character c : substance.toCharArray()) builder.insert(random.nextInt(builder.length() + 1), c);
        return builder.toString();
    }
}
