package ru.nikikorn.backup;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BackupSettings {

    private String targetDirectory;
    private String backupDirectory;
    private boolean includeDirectory;
    private Integer backupCicleSize;

    public void start() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("Config.txt")) {
            properties.load(inputStream);
            targetDirectory = properties.getProperty("targetDirectory","/Users/nikikorn/IdeaProjects/Backup-service/target/");
            backupDirectory = properties.getProperty("backupDirectory","/Users/nikikorn/IdeaProjects/Backup-service/target/backup/");
            includeDirectory = Boolean.parseBoolean(properties.getProperty("includeDirectory","false"));
            backupCicleSize = Integer.valueOf(properties.getProperty("backupCicleSize","3").trim());
        } catch (IOException e) {
            System.out.println("Configuration file was not read - the default settings was applied.");
            targetDirectory = "/Users/nikikorn/IdeaProjects/Backup-service/";
            backupDirectory = "/Users/nikikorn/IdeaProjects/Backup-service/backup/";
            includeDirectory = false;
            backupCicleSize = 3;
        } catch (NumberFormatException e) {
            System.out.println("Incorrect data format in backupCicleSize. Default setting was set.");
            backupCicleSize = 3;
        }
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
