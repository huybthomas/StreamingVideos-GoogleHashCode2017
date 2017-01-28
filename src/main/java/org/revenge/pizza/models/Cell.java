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
}

