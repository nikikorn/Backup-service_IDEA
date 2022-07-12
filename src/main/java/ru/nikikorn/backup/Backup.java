package ru.nikikorn.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import static java.nio.file.Files.*;

public class Backup {
    private BackupSettings settings;

    public Backup(BackupSettings bs) {
        final String welcomeText = String.format("You are trying to backup this directory - %s. " +
                        System.lineSeparator() + "If you type \"Yes\", copies of files will be saved in - %s.",
                bs.getTargetDirectory(), bs.getBackupDirectory());
        System.out.println(welcomeText);
    }

    public static void copy(BackupSettings settings) throws IOException {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy, HH-mm");
        String date = dateTimeFormatter.format(LocalDateTime.now());
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            Path source = Paths.get(settings.getTargetDirectory());
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
                        if (!file.isHidden()) {
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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
            //Check if amount of folder correct

        }
    }

   /* public void deletingDirectories (Path destination) {
        File[] amountOfFolder = destination.getParent().toFile().listFiles();
        assert amountOfFolder != null;
        List<File> listOfFiles = Arrays.stream(amountOfFolder)
                .filter((file) -> !file.isHidden())
                .collect(Collectors.toList());
        //это для проверки
        System.out.println(listOfFiles);
        //
        if (listOfFiles.size() - 1 > settings.getBackupCicleSize()) {
            long fileOldest;
            List<Long> longList = listOfFiles.stream()
                    .map(new Function<File, Long>() {
                        @Override
                        public Long apply(File file) {
                            return file.lastModified();
                        }
                    })
                    .collect(Collectors.toList());
            Collections.sort(longList);
            System.out.println(longList);
            for (int i = 0; i < listOfFiles.size() - 1; i++) {
                if (listOfFiles.get(i).lastModified() < longList.get(i)) {
                    //Files.delete(listOfFiles.get(i).toPath()); // тут нужно провалиться внутрь и удалить каждый файл
                    FileUtils.deleteDirectory(listOfFiles.get(i)); // утилита помогает решить вопрос удаления НЕпустых папок. С ней нет ошибки
                }
            }
            amountOfFolder = destination.getParent().toFile().listFiles();
            List<File> listOfFilesAfter = Arrays.stream(amountOfFolder)
                    .filter((file) -> !file.isHidden())
                    .collect(Collectors.toList());
            System.out.println(listOfFilesAfter);
        }
    }*/
}


