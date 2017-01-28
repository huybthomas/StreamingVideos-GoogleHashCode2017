package org.revenge.pizza.FileHandling;

import org.revenge.pizza.models.Slice;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by arthu on 28/01/2017.
 */
public class FileOutput {

    private BufferedWriter writer;
    private String fileName;

    public FileOutput(String fileName)
    {
        this.fileName = fileName;
    }

    public void OutputResult(int nSlices, List<Slice> slices)
    {
        try
        {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(Integer.toString(nSlices));
            writer.newLine();

            //Write first line (Q: number of drone commands, D: number of drones, T: simulation time)
            parseSlices(slices);


            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void parseSlices(List<Slice> slices){
        for(Slice slice: slices){
            try {
                writer.write(slice.toString());
                writer.newLine();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
