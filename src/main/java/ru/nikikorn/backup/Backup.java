package ru.nikikorn.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.nio.file.Files.*;

public class Backup {
    private Backup(BackupSettings bs) {
    }

    final static String WELCOMETEXT = "You are trying to backup this directory - %s. " +
            System.lineSeparator() + "If you type \"Yes\", copies of files will be saved in - %s.";

    public static void start(BackupSettings settings) throws IOException {
        System.out.printf((WELCOMETEXT), settings.getTargetDirectory(), settings.getBackupDirectory());
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy, HH-mm");
        String date = dateTimeFormatter.format(LocalDateTime.now());

        if (!(scanner.nextLine().equalsIgnoreCase("yes"))) {
            System.exit(1);
        } else {
            Path source = settings.getTargetDirectory();
            Path destination = Paths.get(settings.getBackupDirectory() + "/" + date);
            try {
                Files.createDirectories(destination);
            } catch (FileAlreadyExistsException e) {
                Files.copy(destination, destination, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Files were successfully overwrited.");
            }

            if (settings.isIncludeDirectory()) {
                File[] listFiles = source.toFile().listFiles();
                try {
                    for (File file : listFiles) {
                        if (file.isHidden()) {
                            continue;
                        }
                        if (file.isFile()) {
                            Files.copy(file.toPath(), Paths.get(destination + "/" + file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        } else {
                            Path newDir = null;
                            try {
                                newDir = createDirectory(Paths.get(destination + "/" + file.toPath().getFileName()));
                            } catch (IOException e) {
                                try {
                                    Files.copy(file.toPath(), Paths.get(destination + "/" + file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                                } catch (IOException ex) {
                                    System.out.println("Files were successfully overwrited.");
                                }
                            }
                            File[] inside = file.listFiles();
                            if (!file.isHidden()) {
                                for (File fileInside : inside) {
                                    try {
                                        Files.copy(fileInside.toPath(), Paths.get(newDir + "/" + fileInside.toPath().getFileName()));
                                    } catch (NoSuchFileException e) {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Backup copying problem. Inform you system administrator.");
                }
            } else {
                File[] listFiles = source.toFile().listFiles();
                for (File file : listFiles) {
                    if (!file.isHidden()) {
                        if (file.isFile()) {
                            Files.copy(file.toPath(), Paths.get(destination + "/" + file.toPath().getFileName()), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
            }
        }
    }
}




