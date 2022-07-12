package ru.nikikorn.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.nio.file.Files.*;

public class Backup {


    private final static String WELCOMETEXT = "You are trying to backup this directory - %s. " +
            System.lineSeparator() + "If you type \"Yes\", copies of files will be saved in - %s.";

    public static void start(BackupSettings settings) throws IOException {
        System.out.printf((WELCOMETEXT), settings.getTargetDirectory(), settings.getBackupDirectory());
        Scanner scanner = new Scanner(System.in);

        if (!(scanner.nextLine().equalsIgnoreCase("yes"))) {
            return;
        }
        Path source = settings.getTargetDirectory();
        Path destination = settings.getBackupDirectory();

        Backup.copy(source,destination);

    }

    private static void copy(Path source, Path backupDestination) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy, HH-mm");
        String date = dateTimeFormatter.format(LocalDateTime.now());
        try {
            Files.createDirectories(Path.of(backupDestination + "/" + date));
        } catch (IOException e) {
            System.out.println("No such file");;
        }
        backupDestination = Path.of(backupDestination + "/" + date);
        File[] listFiles = source.toFile().listFiles();
            for (File file : listFiles) {
                if (file.isHidden()) {
                    continue;
                }
                if (file.isFile()) {
                    try {
                        Files.copy(file.toPath(), Paths.get(backupDestination + "/" + file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.out.println("File was successfully overwritted.");
                    }
                } else {
                    try {
                        Files.copy(file.toPath(), Paths.get(backupDestination + "/" + file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                            System.out.println("Directory was successfully overwrited.");
                    }
                }
            }
        }
    }










