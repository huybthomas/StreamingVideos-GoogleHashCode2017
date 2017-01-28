package org.revenge.pizza;

import org.revenge.pizza.FileHandling.FileInput;
import org.revenge.pizza.models.Cell;
import org.revenge.pizza.models.Slice;
import org.revenge.pizza.models.Window;

import java.util.ArrayList;

/**
 * Created by sdecleeen on 28/01/17.
 */
public class Main {

    public static ArrayList<Slice> slices;
    public static int rowSize;
    public static int colSize;
    public static int maxIng;
    public static int minIng;

    public static void main(String[] args) {
        FileInput fileInput = new FileInput();
        fileInput.parseFile("input/big.in");
        slices = new ArrayList<Slice>();
        maxIng = fileInput.maxIngredientsPerSlice;
        minIng = fileInput.minIngredientsPerSlice;
        rowSize = fileInput.nRows;
        colSize = fileInput.nCols;

        Cell[][] pizza = fileInput.pizza;

    }

    public void slicing(Cell[][] pizza)
    {
        //Horizontal checking
        for(int i = 0; i < rowSize; i++)
        {
            horizontalCheck(pizza[i]);
        }

        //Vertical checking

    }

    public void horizontalCheck(Cell[] row)
    {
        Cell it = row[0];

        Slice slice = new Slice();
        Window window = new Window();
        int sliceNumOfTom = 0;
        int sliceNumOfMush = 0;

        while(it.col < colSize)
        {
            Cell.Type type = it.type;

            if(sliceNumOfTom + sliceNumOfMush <= maxIng)
            {

            }
            else
            {
                //remove first element
            }

            if(sliceNumOfTom >= minIng && sliceNumOfMush >= minIng)
            {
                //Smallest slice found
                for(int i = window.startIndex; i <= window.endIndex; i++)
                {
                    row[i];
                    slice.cells.add(row[i]);
                }
            }
        }
    }
}
