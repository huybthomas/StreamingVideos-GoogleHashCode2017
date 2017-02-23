package org.revenge.video;

import org.revenge.pizza.steve.Cell;
import org.revenge.pizza.steve.Slice;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sdecleeen on 23/02/17.
 */
public class Main {

//    private static final String[] INPUT_FILES = {"kittens.in", "me_at_the_zoo.in", "trending_today.in", "videos_worth_spreading.in"};
    private static final String[] INPUT_FILES = {"kittens.in"};

    private static int numberOfVideos;
    private static int numberOfEndPoints;
    private static int numberOfRequestDescriptions;
    private static int numberOfCacheServers;
    private static int cacheServerCapacity;

    private static List<Video> videos;

    public static void main(String[] args) {
        for (String file : INPUT_FILES) {
            readInput(file);
//            solve();
//            printOutput(file);
        }
    }


    private static void readInput(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/video/input/" + fileName))) {
            int[] fileArgs = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            numberOfVideos = fileArgs[0];
            numberOfEndPoints = fileArgs[1];
            numberOfRequestDescriptions = fileArgs[2];
            numberOfCacheServers = fileArgs[3];
            cacheServerCapacity = fileArgs[4];

            int[] vidData = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            videos = new ArrayList<>();
            for(int i = 0; i < vidData.length; i++) {
                videos.add(new Video(i, vidData[i]));
            }

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
