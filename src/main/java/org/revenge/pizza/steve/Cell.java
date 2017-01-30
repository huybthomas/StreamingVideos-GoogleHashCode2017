package org.revenge.pizza.steve;

/**
 * Created by sdecleen.
 */
public class Cell {

    public CellType cellType;

    public boolean isInUse;

    public Cell(char type) {
        setCellType(type);
        isInUse = false;
    }

    private void setCellType(char type) {
        if(type == 'T') {
            cellType = CellType.TOMATO;
        } else {
            cellType = CellType.MUSHROOM;
        }
    }

}

enum CellType {
    TOMATO, MUSHROOM
}
