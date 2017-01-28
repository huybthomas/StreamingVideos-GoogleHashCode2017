package org.revenge.pizza;

import org.revenge.pizza.FileHandling.FileInput;
import org.revenge.pizza.FileHandling.FileOutput;
import org.revenge.pizza.models.Cell;
import org.revenge.pizza.models.Slice;
import org.revenge.pizza.models.Window;

import java.util.ArrayList;
import java.util.List;

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
        fileInput.parseFile("input/big.in");
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

        //Vertical checking

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
}
