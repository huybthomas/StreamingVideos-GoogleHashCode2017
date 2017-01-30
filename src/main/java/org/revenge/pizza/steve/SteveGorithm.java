package org.revenge.pizza.steve;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sdecleen.
 */
public class SteveGorithm {

    private static final String[] INPUT_FILES = {"example.in", "small.in", "medium.in", "big.in"};
//    private static final String[] INPUT_FILES = {"example.in"};

    private static Cell[][] pizza;

    private static int minIngredientsForEachPerSlice;
    private static int maxCellsPerSlice;

    private static List<Slice> slices;

    public static void main(String[] args) {
        for (String file : INPUT_FILES) {
            readInput(file);
            solve();
            printOutput(file);
        }
    }

    private static void readInput(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/input/" + fileName))) {
            int[] fileArgs = Arrays.stream(bufferedReader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            pizza = new Cell[fileArgs[0]][fileArgs[1]];
            minIngredientsForEachPerSlice = fileArgs[2];
            maxCellsPerSlice = fileArgs[3];
            for (int i=0; i<fileArgs[0]; i++) {
                initPizzaRow(i, bufferedReader.readLine());
            }
        } catch (IOException ioe) {
            System.err.println("IOException while trying to setup file access.");
        }
    }

    private static void initPizzaRow(int rowIndex, String input) {
        for (int colIndex = 0; colIndex < input.length(); colIndex++) {
            pizza[rowIndex][colIndex] = new Cell(input.charAt(colIndex));
        }
    }

    private static void solve() {
        slices = new ArrayList<>();
        List<Block> blocksToAnalyze = getBlocksToAnalyze(2 * minIngredientsForEachPerSlice, maxCellsPerSlice);
        for (Block block : blocksToAnalyze) {
            analyzeBlockInPizza(block);
        }
        expandSlices();
    }

    private static List<Block> getBlocksToAnalyze(int minSliceSize, int maxSliceSize) {
        List<Block> blocksToAnalyze = new ArrayList<>();
        for (int i = minSliceSize; i <= maxSliceSize; i++) {
            for (int j = 1; j <= i; j++) {
                if(i % j == 0) {
                    blocksToAnalyze.add(new Block(j, i / j));
                }
            }
        }
        Collections.sort(blocksToAnalyze, new BlockComparator());
        return blocksToAnalyze;
    }

    private static void analyzeBlockInPizza(Block block) {
        for (int rowIndex = 0; rowIndex <= pizza.length - block.height; rowIndex++) {
            for (int colIndex = 0; colIndex <= pizza[0].length - block.width; colIndex++) {
                if(blockIsAvailable(rowIndex, colIndex, block) && blockHasTheProperIngredients(rowIndex, colIndex, block)) {
                    slices.add(new Slice(rowIndex, rowIndex + block.height - 1, colIndex, colIndex + block.width - 1));
                    setSliceCellsInUse(slices.get(slices.size() - 1));
                }
            }
        }
    }

    private static boolean blockIsAvailable(int currentRow, int currentCol, Block block) {
        for (int rowIndex = currentRow; rowIndex < currentRow + block.height; rowIndex++) {
            for (int colIndex = currentCol; colIndex < currentCol + block.width; colIndex++) {
                if(pizza[rowIndex][colIndex].isInUse) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean blockHasTheProperIngredients(int currentRow, int currentCol, Block block) {
        int tomatoCount = 0;
        int mushroomCount = 0;
        for (int rowIndex = currentRow; rowIndex < currentRow + block.height; rowIndex++) {
            for (int colIndex = currentCol; colIndex < currentCol + block.width; colIndex++) {
                if (CellType.TOMATO.equals(pizza[rowIndex][colIndex].cellType)) {
                    tomatoCount++;
                } else {
                    mushroomCount++;
                }
                if (tomatoCount >= minIngredientsForEachPerSlice && mushroomCount >= minIngredientsForEachPerSlice) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void setSliceCellsInUse(Slice slice) {
        for (int rowIndex = slice.r1; rowIndex <= slice.r2; rowIndex++) {
            for (int colIndex = slice.c1; colIndex <= slice.c2; colIndex++) {
                pizza[rowIndex][colIndex].isInUse = true;
            }
        }
    }
    
    private static void expandSlices() {
        while(true) {
            boolean hasExpandedInThisIteration = false;
            for (Slice slice : slices) {
                if (canBeHorizontallyExpanded(slice)) {
                    if (hasRoomForExpansionLeft(slice)) {
                        expandLeft(slice);
                        hasExpandedInThisIteration = true;
                    } else if (hasRoomForExpansionRight(slice)) {
                        expandRight(slice);
                        hasExpandedInThisIteration = true;
                    }
                }
                if (canBeVerticallyExpanded(slice)) {
                    if(hasRoomForExpansionUp(slice)) {
                        expandUp(slice);
                        hasExpandedInThisIteration = true;
                    } else if(hasRoomForExpansionDown(slice)) {
                        expandDown(slice);
                        hasExpandedInThisIteration = true;
                    }
                }
            }
            if(!hasExpandedInThisIteration) {
                break;
            }
        }
    }

    private static boolean canBeHorizontallyExpanded(Slice slice) {
        return (slice.c2 - slice.c1 + 2) * (slice.r2 - slice.r1 + 1) <= maxCellsPerSlice;
    }

    private static boolean hasRoomForExpansionLeft(Slice slice) {
        if(slice.c1 == 0) {
            return false;
        }
        for (int rowIndex = slice.r1; rowIndex <= slice.r2; rowIndex++) {
            if(pizza[rowIndex][slice.c1 - 1].isInUse) {
                return false;
            }
        }
        return true;
    }

    private static void expandLeft(Slice slice) {
        slice.c1 = slice.c1 - 1;
        for (int rowIndex = slice.r1; rowIndex <= slice.r2; rowIndex++) {
            pizza[rowIndex][slice.c1].isInUse = true;
        }
    }

    private static boolean hasRoomForExpansionRight(Slice slice) {
        if(slice.c2 == pizza[0].length - 1) {
            return false;
        }
        for (int rowIndex = slice.r1; rowIndex <= slice.r2; rowIndex++) {
            if(pizza[rowIndex][slice.c2 + 1].isInUse) {
                return false;
            }
        }
        return true;
    }

    private static void expandRight(Slice slice) {
        slice.c2 = slice.c2 + 1;
        for (int rowIndex = slice.r1; rowIndex <= slice.r2; rowIndex++) {
            pizza[rowIndex][slice.c2].isInUse = true;
        }
    }

    private static boolean canBeVerticallyExpanded(Slice slice) {
        return (slice.c2 - slice.c1 + 1) * (slice.r2 - slice.r1 + 2) <= maxCellsPerSlice;
    }

    private static boolean hasRoomForExpansionUp(Slice slice) {
        if(slice.r1 == 0) {
            return false;
        }
        for (int colIndex = slice.c1; colIndex <= slice.c2; colIndex++) {
            if(pizza[slice.r1 - 1][colIndex].isInUse) {
                return false;
            }
        }
        return true;
    }

    private static void expandUp(Slice slice) {
        slice.r1 = slice.r1 - 1;
        for (int colIndex = slice.c1; colIndex <= slice.c2; colIndex++) {
            pizza[slice.r1][colIndex].isInUse = true;
        }
    }

    private static boolean hasRoomForExpansionDown(Slice slice) {
        if(slice.r2 == pizza.length - 1) {
            return false;
        }
        for (int colIndex = slice.c1; colIndex <= slice.c2; colIndex++) {
            if(pizza[slice.r2 + 1][colIndex].isInUse) {
                return false;
            }
        }
        return true;
    }

    private static void expandDown(Slice slice) {
        slice.r2 = slice.r2 + 1;
        for (int colIndex = slice.c1; colIndex <= slice.c2; colIndex++) {
            pizza[slice.r2][colIndex].isInUse = true;
        }
    }

    private static void printOutput(String file) {
        String outputFilePath = file.substring(0, file.indexOf(".")) + ".out";
        int score = 0;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/output/" + outputFilePath))) {
            bufferedWriter.write(Integer.toString(slices.size()));
            bufferedWriter.newLine();
            for (Slice slice : slices) {
                score += (slice.r2 - slice.r1 + 1) * (slice.c2 - slice.c1 + 1);
                bufferedWriter.write(slice.r1 + " " + slice.c1 + " " + slice.r2 + " " + slice.c2);
                bufferedWriter.newLine();
            }
            System.out.println("Score for " + file + ": " + score + "/" + (pizza.length * pizza[0].length));
        } catch(IOException ioe) {
            System.err.println("IOException while trying to setup file output.");
        }
    }

}
