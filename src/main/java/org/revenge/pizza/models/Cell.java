package org.revenge.pizza.models;

/**
 * Created by Thomas on 28/01/2017.
 */
public class Cell
{
    public int row, col;

    public Type type;

    public boolean inUse = false;

    public enum Type
    {
        tomato,
        mushroom
    }

    public int compareTo(Cell c){
        if(this.row == c.row){
            if(this.col == c.col)
                return 0;
            else if(c.col > this.col)
                return 1;
            else
                return -1;
        }else if(c.row > this.row)
            return 1;
        else
            return -1;
    }
}

