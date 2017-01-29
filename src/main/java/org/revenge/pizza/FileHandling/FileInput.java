package org.revenge.pizza.FileHandling;

/**
 * Created by arthu on 28/01/2017.
 */

import org.revenge.pizza.models.Cell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileInput {
    private BufferedReader reader;
    private String sCurrentLine;
    private int line;

    public int nRows;
    public int nCols;
    public int minIngredientsPerSlice;
    public int maxIngredientsPerSlice;

    //Pizza
    public Cell[][] pizza;

    public void parseFile(String fileName)
    {
        line = 1;

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String file = classLoader.getResource(fileName).getFile();
            System.out.println(file);
            reader = new BufferedReader(new FileReader(file));
            sCurrentLine = reader.readLine();
            line++;
            //Read First Line
            String sCurrLineSplit[] = sCurrentLine.split(" ");

            nRows = Integer.parseInt(sCurrLineSplit[0]);
            nCols = Integer.parseInt(sCurrLineSplit[1]);
            minIngredientsPerSlice = Integer.parseInt(sCurrLineSplit[2]);
            maxIngredientsPerSlice = Integer.parseInt(sCurrLineSplit[3]);

            pizza = new Cell[nRows][nCols];
            parsePizza(nCols, nRows);

            System.out.println("File Parsed");
        /*
        while ((sCurrentLine != null && !sCurrentLine.contains("]"))) {
            switch(sCurrentLine){
                case "OBJECTS[":{
                    System.out.println("\"OBJECTS\" detected, parsing...");
                    break;
                }
            }
            sCurrentLine = reader.readLine();
            line++;
        }
        */

        }catch(StringIndexOutOfBoundsException e){
            System.err.println("Error occured at line " + line);
        }
        catch (IOException e) {
            System.err.println("Error occured at line " + line);
        } catch (NumberFormatException e) {
            System.err.println("Number parsing error occured at line " + line);
        } finally {
            try {
                if (reader != null)reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void parsePizza(int nCols, int nRows){
        int row = 0;

        for(row = 0; row < nRows; row++){
            try {
                sCurrentLine = reader.readLine();
                line++;
                parseLine(row, nCols, sCurrentLine);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private void parseLine(int row, int length, String line){
        for(int i = 0; i < length; i++){
            Cell cell = new Cell();

            switch(line.charAt(i))
            {
                case 'T':
                    cell.type = Cell.Type.tomato;
                    cell.row = row;
                    cell.col=i;
                    break;
                case 'M':
                    cell.type = Cell.Type.mushroom;
                    cell.row = row;
                    cell.col=i;
                    break;
            }
            pizza[row][i] = cell;
        }
    }
}
