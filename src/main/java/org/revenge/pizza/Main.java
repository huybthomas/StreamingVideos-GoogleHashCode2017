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

    public static List<Slice> slices;

    public static void main(String[] args) {
        FileInput fileInput = new FileInput();
        fileInput.parseFile("input/small.in");
        slices = new ArrayList<Slice>();
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
        FileOutput fileOutput = new FileOutput("resultSmall.out");

        calculateCoverage();

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

        expandSlices(pizza, slices);

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
                if(isWindowAvailable(pizza, ci, ci, ri, ri + maxWindowSize-1)
                        && hasIngredients(pizza, ci, ci, ri, ri + maxWindowSize-1, minIng)) {
                    slices.add(getSliceVertical(pizza, ci, ri, ri + maxWindowSize));
                }
            }
        }

        return slices;
    }

    //pizza, col, row start, row end
    public static boolean isWindowAvailable(Cell[][] pizza, int cs, int ce, int rs, int re) {
        if(cs < 0 || ce >= colSize || rs < 0 || re >= rowSize)
            return false;
        for(int i = rs; i<= re; i++) {
            for (int j = cs; j <= ce; j++){
                if (pizza[i][j].inUse) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    * @param  l  Min Num Of Each Ingredient
    */
    public static boolean hasIngredients(Cell[][] pizza, int cs, int ce, int rs, int re, int l) {
        int noOfTomatoes = 0;
        int noOfMushrooms = 0;
        for(int i = rs; i <= re; i++) {
            for(int j = cs; j <= ce; j++) {
                if (pizza[i][j].type.equals(Cell.Type.mushroom)) {
                    noOfMushrooms++;
                }
                if (pizza[i][j].type.equals(Cell.Type.tomato)) {
                    noOfTomatoes++;
                }
                if (noOfTomatoes >= l && noOfMushrooms >= l) {
                    return true;
                }
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

    public static void expandSlices(Cell[][] pizza, List<Slice> slices){
        boolean changed = true;
        while(changed){
            changed = false;
            for(Slice slice: slices){
                if(expandSlice(pizza, slice)){
                    changed = true;
                }
            }
        }
    }

    public static boolean expandSlice(Cell[][] pizza, Slice slice){
        if(slice.getNumCells() > maxIng)
            return false;
        int[][] bounds = slice.getBounds();
        int rs, re, cs, ce;
        rs = bounds[0][0];
        re = bounds[1][0];
        cs = bounds[0][1];
        ce = bounds[1][1];

        //Current Slice's width and height
        //+1 because otherwise size can be 0
        int width = ce - cs +1;
        int height = re - rs +1;

        //check left
        if(isWindowAvailable(pizza, cs-1, cs-1, rs, re)){
            //if total amount of ingredients would not exceed max
            if(!((slice.getNumCells() + height) > maxIng)) {
                for (int i = rs; i <= re; i++) {
                    slice.cells.add(pizza[i][cs - 1]);
                    pizza[i][cs - 1].inUse=true;
                }
                return true;
            }
        }

        //check right
        if(isWindowAvailable(pizza, ce+1, ce+1, rs, re)){
            //if total amount of ingredients would not exceed max
            if(!((slice.getNumCells() + height) > maxIng)) {
                for (int i = rs; i <= re; i++) {
                    slice.cells.add(pizza[i][ce + 1]);
                    pizza[i][ce + 1].inUse=true;
                }
                return true;
            }
        }

        //check up
        if(isWindowAvailable(pizza, cs, ce, rs-1, rs-1)){
            //if total amount of ingredients would not exceed max
            if(!((slice.getNumCells() + width) > maxIng)) {
                for (int i = cs; i <= ce; i++) {
                    slice.cells.add(pizza[rs-1][i]);
                    pizza[rs-1][i].inUse=true;
                }
                return true;
            }
        }

        //check down
        if(isWindowAvailable(pizza, cs, ce, re+1, re+1)){
            //if total amount of ingredients would not exceed max
            if(!((slice.getNumCells() + width) > maxIng)) {
                for (int i = cs; i <= ce; i++) {
                    slice.cells.add(pizza[re+1][i]);
                    pizza[re+1][i].inUse=true;
                }
                return true;
            }
        }

        return false;
    }

    public static void calculateCoverage(){
        double numCellsCovered = 0;
        for(Slice slice: slices){
            numCellsCovered += slice.cells.size();
        }
        //System.out.println("Number of cells: " + rowSize*colSize);
        //System.out.println("Number of cells sliced: " + numCellsCovered);
        System.out.println("Pizza coverage: " + numCellsCovered/(rowSize*colSize));
    }
}
