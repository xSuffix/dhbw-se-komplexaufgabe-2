package storage;

import material.Block;

public class Storage {
    private final Block[] blocks;

    public Storage(int size) {
        blocks = new Block[size];
    }

    public Block getBlock(int index) {
        try {
            return blocks[index];
        } catch (IndexOutOfBoundsException e) {
            System.err.printf("[storage.Storage] Index %d is out of bounds%n", index);
            return null;
        }
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public void fillStorage() {
        for (int i = 0; i < blocks.length; i++)
            if (blocks[i] == null) blocks[i] = new Block();
    }
}
