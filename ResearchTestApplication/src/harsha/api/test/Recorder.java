/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package harsha.api.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 *
 * @author harshasraja
 */
public class Recorder {

    private String code = "";
    private String description = "";
    private String logPath = ".";
    private long startTime;
    private long stopTime;
    private Writer logWriter;

    public Recorder() {
    }

    public Recorder(String code, String description) {
    }

    public Recorder(String code, String description, String logPath) {
        this.code = code;
        this.description = description;
        this.logPath = logPath;
    }

    public void initialize() throws Exception {
        File file = new File(logPath + "/" + code + ".log");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        logWriter = new BufferedWriter(new FileWriter(file));
    }

    public void start() {
        setStartTime(System.nanoTime());
    }

    public void stop() {
        setStopTime(System.nanoTime());
    }

    public long ellapsedTime() {
        return System.nanoTime() - getStartTime();
    }

    public long duration() {
        return getStopTime() - getStartTime();
    }

    public void close() {
        try {
            logWriter.flush();
            logWriter.close();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void log(String info) {
        try {
            logWriter.write(info);
            logWriter.flush();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
