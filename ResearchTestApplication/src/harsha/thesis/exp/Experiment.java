/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.thesis.exp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 *
 * @author jcrada
 */
public class Experiment {

    private String code = "";
    private String description = "";
    private String logPath = ".";
    private long startTime;
    private long stopTime;
    private Writer logWriter;

    public Experiment() {
    }

    public Experiment(String code, String description) {
    }

    public Experiment(String code, String description, String logPath) {
        this.code = code;
        this.description = description;
        this.logPath = logPath;
    }

    public void initialize() throws Exception{
        File file = new File(logPath + "/" + code + ".log");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        logWriter = new BufferedWriter(new FileWriter(file));
    }
    
    public void start()  {
        setStartTime(System.currentTimeMillis());
    }

    public void stop()  {
        setStopTime(System.currentTimeMillis());
    }
    
    public long ellapsedTime(){
        return System.currentTimeMillis() - getStartTime();
    }
    
    public long duration(){
        return getStopTime() - getStartTime();
    }
    
    public void destroy() throws Exception{
        logWriter.flush();
        logWriter.close();
    }

    
    public void log(String info) throws Exception {
        logWriter.write(info);
        logWriter.flush();
    }

    public Writer writer() {
        return logWriter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }
}
