package org.revenge.pizza;

import org.revenge.pizza.FileHandling.FileInput;

/**
 * Created by sdecleeen on 28/01/17.
 */
public class Main {

    public static void main(String[] args) {
        FileInput fileInput = new FileInput();
        fileInput.parseFile("input/big.in");
    }

}
