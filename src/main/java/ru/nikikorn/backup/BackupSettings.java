package ru.nikikorn.backup;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class BackupSettings {

    private String targetDirectory;
    private String backupDirectory;
    private boolean includeDirectory;
    private Integer backupCicleSize;
    private String  jar = Path.of(BackupServiceApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent().normalize() +"/";

    public BackupSettings () {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("Config.txt")) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Configuration file was not read - the default settings was applied.");
        }

        targetDirectory = properties.getProperty("targetDirectory", jar);
        backupDirectory = properties.getProperty("backupDirectory", jar +"backup/");
        includeDirectory = Boolean.parseBoolean(properties.getProperty("includeDirectory", "false"));
        backupCicleSize = Integer.valueOf(properties.getProperty("backupCicleSize", "3").trim());
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public String getBackupDirectory() {
        return backupDirectory;
    }

    public boolean getIncludeDirectory() {
        return includeDirectory;
    }

    public Integer getBackupCicleSize() {
        return backupCicleSize;
    }
}
