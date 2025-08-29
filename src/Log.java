/**
 * Log: Used to log important user activity to txt file
 * @author Alan Holguin
 * @author Christian Odom
 * @author Jorge Sandoval
 * @author Sabas Rojas
 */
import java.io.File;
import java.io.FileWriter;

public class Log{
    
    //ATTRIBUTES
    private String fileName;

    /**
     * @param filename
     * @brief create an instance of Log with file name to log data
     */
    public Log(String fileNameIn){
        this.fileName = fileNameIn;
    }

    //METHODS

    /**
     * @brief create file to log data
     * Written by: Christian Odom
     */
    public void createLogFile(){
        try {
            File logFile = new File(this.fileName);
            logFile.createNewFile();

        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    /**
     * @param string
     * @brief log updates
     * Written by: Christian Odom
     */
    public void logActivity(String updateIn){
        try {
            FileWriter logActivity = new FileWriter("log.txt", true);
            logActivity.write(updateIn + "\n");
            logActivity.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }
}