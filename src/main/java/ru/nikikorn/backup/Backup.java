package ru.nikikorn.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Backup {

    private final static String WELCOME_TEXT = "You are trying to backup this directory - %s. " +
            System.lineSeparator() + "If you type \"Yes\", copies of files will be saved in - %s.";

    public static void start(BackupSettings settings) throws IOException {
        System.out.printf((WELCOME_TEXT), settings.getTargetDirectory(), settings.getBackupDirectory());
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy, HH-mm");
        String date = dateTimeFormatter.format(LocalDateTime.now());

        if (!(scanner.nextLine().equalsIgnoreCase("yes"))) {
            return;
        }
        Path source = settings.getTargetDirectory();
        Path destination = Path.of(settings.getBackupDirectory() + "/" + date);
        Files.createDirectory(destination); //TODO добавить логику если дир существует

        Backup.copy(source, destination, settings.isIncludeDirectory());
    }

    private static void copy(Path source, Path backupDestination, boolean isIncludeDirectory) throws IOException {
        File[] listFiles = source.toFile().listFiles();
        if (listFiles == null) return;
        for (File file : listFiles) {
            if (file.isHidden()) {
                continue;
            } else if (file.isFile()) {
                Files.copy(file.toPath(), Paths.get(backupDestination + "/" + file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } else if (file.isDirectory() && isIncludeDirectory) {
                Path temp = Paths.get(backupDestination + "/" + file.toPath().getFileName());
                Files.createDirectory(temp);
                copy(file.toPath(),temp, isIncludeDirectory);
            }
        }
    }
}










