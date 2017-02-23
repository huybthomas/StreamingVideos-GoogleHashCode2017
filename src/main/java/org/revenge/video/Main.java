package org.revenge.video;

import org.revenge.pizza.steve.Cell;
import org.revenge.pizza.steve.Slice;

import java.io.*;
import java.util.Arrays;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class Main {

    private static final String[] INPUT_FILES = {"", "", "", ""};

    public static void main(String[] args) {
        for (String file : INPUT_FILES) {
            readInput(file);
//            solve();
//            printOutput(file);
        }
    }


    private static void readInput(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/video/input/" + fileName))) {
//            int[] fileArgs = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
//            pizza = new Cell[fileArgs[0]][fileArgs[1]];
//            minIngredientsForEachPerSlice = fileArgs[2];
//            maxCellsPerSlice = fileArgs[3];
//            for (int i=0; i<fileArgs[0]; i++) {
//                initPizzaRow(i, bufferedReader.readLine());
//            }
        } catch (IOException ioe) {
            System.err.println("IOException while trying to setup file access.");
        }
    }

    private static void printOutput(String file) {
        String outputFilePath = file.substring(0, file.indexOf(".")) + ".out";
//        int score = 0;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/video/output/" + outputFilePath))) {
//            bufferedWriter.write(Integer.toString(slices.size()));
//            bufferedWriter.newLine();
//            for (Slice slice : slices) {
//                score += (slice.r2 - slice.r1 + 1) * (slice.c2 - slice.c1 + 1);
//                bufferedWriter.write(slice.r1 + " " + slice.c1 + " " + slice.r2 + " " + slice.c2);
//                bufferedWriter.newLine();
//            }
//            System.out.println("Score for " + file + ": " + score + "/" + (pizza.length * pizza[0].length));
        } catch(IOException ioe) {
            System.err.println("IOException while trying to setup file output.");
        }
    }

}
