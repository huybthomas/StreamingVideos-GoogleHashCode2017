package org.revenge.pizza.steve;

import java.util.Comparator;

/**
 * Created by sdecleen.
 */
public class Block {

    public int width;
    public int height;

    public Block(int width, int height) {
        this.width = width;
        this.height = height;
    }

}

class BlockComparator implements Comparator<Block> {

    @Override
    public int compare(Block block1, Block block2) {
        int block1Sum = block1.height + block1.width;
        int block2Sum = block2.height + block2.width;
        if(block1Sum < block2Sum) {
            return 1;
        } else if(block1Sum == block2Sum) {
            if(block1.width < block2.width) {
                return 1;
            } else if(block1.width == block2.width) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

}
