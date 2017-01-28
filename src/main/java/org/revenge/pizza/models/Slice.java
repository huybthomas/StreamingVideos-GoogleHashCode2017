package org.revenge.pizza.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Thomas on 28/01/2017.
 */
public class Slice
{
    public ArrayList<Cell> cells;

    public Slice(){};

    @Override
    public String toString(){

        Collections.sort(cells, new Comparator<Cell>() {
            @Override
            public int compare(Cell p0, Cell p1) {
                return p0.compareTo(p1);
            }
        });

        StringBuilder s = new StringBuilder();
        s.append(cells.get(0).row);
        s.append(" ");
        s.append(cells.get(cells.size()).row);
        s.append(" ");
        s.append(cells.get(0).col);
        s.append(" ");
        s.append(cells.get(cells.size()).col);

        return s.toString();
    }
}
