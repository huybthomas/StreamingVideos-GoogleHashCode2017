package org.revenge.pizza;

import org.revenge.pizza.FileHandling.FileInput;
import org.revenge.pizza.FileHandling.FileOutput;
import org.revenge.pizza.models.Cell;
import org.revenge.pizza.models.Slice;
import org.revenge.pizza.models.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by sdecleeen on 28/01/17.
 */
public class Main {

    public static int rowSize;
    public static int colSize;
    public static int maxIng;
    public static int minIng;

    public static void main(String[] args) {
        FileInput fileInput = new FileInput();
        fileInput.parseFile("input/small.in");
        List<Slice> slices = new ArrayList<Slice>();
        maxIng = fileInput.maxIngredientsPerSlice;
        minIng = fileInput.minIngredientsPerSlice;
        rowSize = fileInput.nRows;
        colSize = fileInput.nCols;

        Cell[][] pizza = fileInput.pizza;

        slices = slicing(pizza);

        for(Slice slice : slices)
        {
            System.out.println(slice.toString());
        }

        //Write to file
        FileOutput fileOutput = new FileOutput("resultBig.out");

        fileOutput.OutputResult(slices.size(), slices);
    }

    public static List<Slice> slicing(Cell[][] pizza)
    {
        List<Slice> slices = new ArrayList<Slice>();

        //Horizontal checking
        for(int i = 0; i < rowSize; i++)
        {
            slices.addAll(horizontalCheck(pizza[i]));
        }

        slices.addAll(verticalCheck(pizza));

        return slices;
    }

    public static List<Slice> horizontalCheck(Cell[] row)
    {
        int cellIt = 0;
        List<Slice> slices = new ArrayList<Slice>();

        Slice slice = new Slice();
        Window window = new Window();
        window.endIndex = -1;
        int sliceNumOfTom = 0;
        int sliceNumOfMush = 0;

        while(cellIt < colSize)
        {
            Cell it = row[cellIt];
            Cell.Type type = it.type;

            //if(sliceNumOfTom + sliceNumOfMush >= maxIng)
            if(sliceNumOfTom + sliceNumOfMush >= 2*minIng)
            {
                if(row[window.startIndex].type == Cell.Type.tomato)
                {
                    sliceNumOfTom--;
                }
                else
                {
                    sliceNumOfMush--;
                }

                //remove first element
                window.startIndex++;

            }

            if(it.type == Cell.Type.tomato)
            {
                sliceNumOfTom++;
            }
            else
            {
                sliceNumOfMush++;
            }

            window.endIndex++;

            if(sliceNumOfTom >= minIng && sliceNumOfMush >= minIng)
            {
                //Smallest slice found
                for(int i = window.startIndex; i <= window.endIndex; i++)
                {
                    row[i].inUse = true;
                    slice.cells.add(row[i]);
                }

                slices.add(slice);

                //Reset slice + window
                slice = new Slice();
                window = new Window();
                sliceNumOfTom = 0;
                sliceNumOfMush = 0;
                window.endIndex = it.col;
                window.startIndex = it.col + 1;
            }

            cellIt++;
        }

        return slices;
    }

    public static List<Slice> verticalCheck(Cell[][] pizza)
    {
        List<Slice> slices = new ArrayList<Slice>();

        int maxWindowSize = minIng * 2;

        for(int ci = 0; ci < colSize; ci++) {
            for(int ri = 0; ri < rowSize - maxWindowSize; ri++) {
                if(isWindowAvailable(pizza, ci, ri, ri + maxWindowSize)
                        && hasIngredients(pizza, ci, ri, ri + maxWindowSize, minIng)) {
                    slices.add(getSliceVertical(pizza, ci, ri, ri + maxWindowSize));
                }
            }
        }

        return slices;
    }

    public static boolean isWindowAvailable(Cell[][] pizza, int col, int rs, int re) {
        for(int i = rs; i< re; i++) {
            if(pizza[i][col].inUse) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasIngredients(Cell[][] pizza, int col, int rs, int re, int l) {
        int noOfTomatoes = 0;
        int noOfMushrooms = 0;
        for(int i = rs; i < re; i++) {
            if(pizza[i][col].type.equals(Cell.Type.mushroom)) {
                noOfMushrooms++;
            }
            if(pizza[i][col].type.equals(Cell.Type.tomato)) {
                noOfTomatoes++;
            }
            if(noOfTomatoes >= l && noOfMushrooms >= l) {
                return true;
            }
        }
        return false;
    }

    public static Slice getSliceVertical(Cell[][] pizza, int col, int rs, int re) {
        Slice slice = new Slice();
        for(int i = rs; i< re; i++) {
            pizza[i][col].inUse = true;
            slice.cells.add(pizza[i][col]);
        }
        return slice;
    }

}
