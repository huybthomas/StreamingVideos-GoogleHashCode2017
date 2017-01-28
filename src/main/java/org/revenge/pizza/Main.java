package org.revenge.pizza;

import org.revenge.pizza.FileHandling.FileInput;
import org.revenge.pizza.models.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by sdecleeen on 28/01/17.
 */
public class Main {

    public static void main(String[] args) {
        FileInput fileInput = new FileInput();
        fileInput.parseFile("input/big.in");
    }

    public static List<int[]> getBlockDimensionsToAnalyze(int h, int l) {
        l = l*2;
        List<int[]> divisors = new ArrayList<>();

        boolean stop = false;
        for(int i = 1; i <= h; i++) {
            int j = h/i;
            divisors.add(new int[]{i, j});
            for(int k = 0; k<divisors.size(); k++) {
                if(i == divisors.get(k)[1]) {
                    stop = true;
                    break;
                }
            }
            if(stop) {
                break;
            }
        }

        int cSize = divisors.size();
        for(int i=0; i<cSize; i++) {
            divisors.add(new int[]{divisors.get(i)[1], divisors.get(i)[0]});
        }

        return divisors;
    }

}
