import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Loader {
    LinkedList<ProcessControlBlock> queue;
    Disk disk;

    /*public static void main(String args[])
    {
        Loader l = new Loader();
        l.readProgramFile();
    }*/

    public void readProgramFile()
    {
        String attributes = "";
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader("ProgramFile.txt"));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null)
            {
                /*
                When line contains // check for:
                // JOB hex hex hex
                // END
                // DATA hex hex hex
                 */
                if (line.contains("//"))
                {
                    if (line.contains("JOB"))
                    {
                        attributes = ""; // reset attributes
                        attributes = line + " ";
                    }
                    else if (line.contains("Data") && !attributes.equals(""))
                    {
                        attributes += line;
                        addJobToPCB(attributes);
                    }
                }
                else
                {
                    //disk.addToDisk(line);
                }
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    } // end of readProgramFile()

    /*
    Add Process Control block for new process to queue object
    String format: // JOB a b c // Data d e f
     */
    public void addJobToPCB(String data)
    {
        String jobID;
        String numOfWords;
        String priority_num;
        String inputBuff;
        String outputBuff;
        String tempBuff;

        String[] splitLine = data.split("\\s+");

        jobID = splitLine[2];
        int jobID_num = Integer.parseInt(jobID, 16); // example of converting hex to decimal

        numOfWords = splitLine[3];
        priority_num = splitLine[4];
        inputBuff = splitLine[7];
        outputBuff = splitLine[8];
        tempBuff = splitLine[9];

        queue.add(new ProcessControlBlock(jobID, numOfWords, priority_num, inputBuff, outputBuff, tempBuff));
    }

}