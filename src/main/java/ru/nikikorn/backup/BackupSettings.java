package ru.nikikorn.backup;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class BackupSettings {

    private final String targetDirectory;
    private final String backupDirectory;
    private final boolean includeDirectory;
    private final Integer backupCycleSize;

    public BackupSettings() {
        String jar = Path.of(BackupServiceApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent().normalize() + "/";
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("Config.txt")) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Configuration file was not read - the default settings was applied.");
        }
        targetDirectory = properties.getProperty("targetDirectory", jar);
        backupDirectory = properties.getProperty("backupDirectory", jar + "backup/");
        includeDirectory = Boolean.parseBoolean(properties.getProperty("includeDirectory", "false"));
        backupCycleSize = Integer.valueOf(properties.getProperty("backupCycleSize", "3").trim());
    }

    public Path getTargetDirectory() {
        return Path.of(targetDirectory);
    }

    public Path getBackupDirectory() {
        return Path.of(backupDirectory);
    }

    public boolean isIncludeDirectory() {
        return includeDirectory;
    }

    public Integer getBackupCycleSize() {
        return backupCycleSize;
    }
}
