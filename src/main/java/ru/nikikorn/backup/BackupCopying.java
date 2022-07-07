package ru.nikikorn.backup;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import static java.nio.file.Files.*;

public class BackupCopying {
    String welcomeText = String.format("You are trying to backup this directory - %s. " +
                    "\nIf you type \"Yes\", copies of files will be saved in - %s",
            new BackupSettings().getTargetDirectory(), new BackupSettings().getBackupDirectory());


    public void copy() throws IOException {
        System.out.println(welcomeText);
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter localDate = DateTimeFormatter.ofPattern("MM-dd-yyyy, HH-mm");
        String date = localDate.format(LocalDateTime.now());
        if ((new String(scanner.nextLine())).equalsIgnoreCase("yes")) {
            Path source = Paths.get(new BackupSettings().getTargetDirectory());
            Path destination = Paths.get(new BackupSettings().getBackupDirectory() + "/" + date);
            try {
                Files.createDirectories(destination);
            } catch (FileAlreadyExistsException e) {
                System.out.println("Files were saved less than a minute ago, wait 60 sec and try again.");
            }

            if (new BackupSettings().getIncludeDirectory()) {
                File[] listFiles = source.toFile().listFiles();
                try {
                    for (File file : listFiles) {
                        if (!file.isHidden()) {
                            if (file.isFile()) {
                                Files.copy(file.toPath(), Paths.get(destination + "/" + file.toPath().getFileName()));
                            } else {
                                Path newDir = null;
                                try {
                                    newDir = createDirectory(Paths.get(destination + "/" + file.toPath().getFileName()));
                                } catch (FileAlreadyExistsException e) {
                                    System.out.println("Files were saved less than a minute ago, wait 60 sec and try again.");
                                    break;
                                }
                                File[] inside = file.listFiles();
                                if (!file.isHidden()) {
                                    for (File fileInside : inside) {
                                        try {
                                            Files.copy(fileInside.toPath(), Paths.get(newDir + "/" + fileInside.toPath().getFileName()));
                                        } catch (NoSuchFileException e) {
                                            System.out.println("Files were saved less than a minute ago, wait 60 sec and try again.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                File[] listFiles = source.toFile().listFiles();
                for (File file : listFiles) {
                    if (!file.isHidden()) {
                        if (file.isFile()) {
                            Files.copy(file.toPath(), Paths.get(destination + "/" + file.toPath().getFileName()));
                        }
                    }
                }
            }
            //Check if amount of folder correct
           /* File[] amountOfFolder = destination.getParent().toFile().listFiles();
            List<File> listOfFiles = new ArrayList<>();
            assert amountOfFolder != null;
            Collections.addAll(listOfFiles, amountOfFolder);
            listOfFiles.stream().filter(;
            //это для проверки
            System.out.println(listOfFiles);
            if (listOfFiles.size() > new BackupSettings().getBackupCicleSize()) {
                System.out.println("BLOCK подсчета папок");
                listOfFiles.sort(new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        return (int)(o1.lastModified() - o2.lastModified());
                    }
                });
                for (int i = 3; i < listOfFiles.size(); i++) {
                    if (!listOfFiles.get(i).isHidden()) {

                    }
                }
            }*/
        }
    }
}

