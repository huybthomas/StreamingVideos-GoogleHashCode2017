package org.revenge.pizza.models;

import java.util.ArrayList;

/**
 * Created by Thomas on 28/01/2017.
 */
public class Slice
{
    public ArrayList<Cell> cells;

    public Slice(){};

    public int getNumbOfType(Cell.Type type)
    {
        int num = 0;

        for(Cell cell : cells)
        {
            if(cell.type == type)
            {
                num++;
            }
        }

        return num;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(cells.get(0).row);
        s.append(" ");
        s.append(cells.get(0).col);
        s.append(" ");
        s.append(cells.get(cells.size()).col);
        s.append(" ");
        s.append(cells.get(cells.size()).col);

        return s.toString();
    }
}
