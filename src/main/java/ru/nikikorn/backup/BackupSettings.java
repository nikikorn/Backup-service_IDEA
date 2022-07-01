package ru.nikikorn.backup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BackupSettings {
    private static String targetDirectory;
    private static String backupDirectory;
    private static boolean includeDirectory;
    private static Integer backupCicleSize;

    public void start() throws IOException {
        Properties properties = new Properties();
        try (
                FileInputStream inputStream = new FileInputStream("Config.txt")) {
            properties.load(inputStream);
        } catch (
                FileNotFoundException e) {
            System.out.println("Configuration file was not found - the default settings was applied.");
            targetDirectory = "/Users/nikikorn/IdeaProjects/Backup-service/";
            backupDirectory = "/Users/nikikorn/IdeaProjects/Backup-service/backup/";
            includeDirectory = false;
            backupCicleSize = 3;
        }

        try {
            targetDirectory = properties.getProperty("targetDirectory");
            backupDirectory = properties.getProperty("backupDirectory");
            includeDirectory = Boolean.parseBoolean(properties.getProperty("includeDirectory"));
            backupCicleSize = Integer.valueOf(properties.getProperty("backupCicleSize").trim());
        } catch (NumberFormatException e) {
            System.out.println("Incorrect data format in backupCicleSize. Deafult setting was set.");
            backupCicleSize = 3;
        }
    }

    public static String getTargetDirectory() {
        return targetDirectory;
    }

    public static void setTargetDirectory(String targetDirectory) {
        BackupSettings.targetDirectory = targetDirectory;
    }

    public static String getBackupDirectory() {
        return backupDirectory;
    }

    public static void setBackupDirectory(String backupDirectory) {
        BackupSettings.backupDirectory = backupDirectory;
    }

    public static boolean isIncludeDirectory() {
        return includeDirectory;
    }

    public static void setIncludeDirectory(boolean includeDirectory) {
        BackupSettings.includeDirectory = includeDirectory;
    }

    public static Integer getBackupCicleSize() {
        return backupCicleSize;
    }

    public static void setBackupCicleSize(Integer backupCicleSize) {
        BackupSettings.backupCicleSize = backupCicleSize;
    }
}
