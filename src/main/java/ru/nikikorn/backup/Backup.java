package ru.nikikorn.backup;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if(!destination.toFile().exists()) {
            Files.createDirectory(destination);
            Backup.copy(source, destination, settings.isIncludeDirectory());
        } else {
            Backup.copy(source, destination, settings.isIncludeDirectory());
        }
        deleteOldestDirectories(settings.getBackupDirectory().toFile(),settings.getBackupCycleSize());
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
                if(!temp.toFile().exists()) {
                    Files.createDirectory(temp);
                    copy(file.toPath(), temp, isIncludeDirectory);
                } else {
                    copy(file.toPath(), temp, isIncludeDirectory);
                }
            }
        }
    }

    private static void deleteOldestDirectories(File file, int cycleSize) throws IOException {
        List<File> toDelete = Arrays.asList(file.listFiles());
        toDelete.stream().filter(File::isHidden).collect(Collectors.toList());
        if (file.isDirectory() && cycleSize +1 > 4) {
            List<File> listFiles = Arrays.stream(file.listFiles()).toList().stream()
                    .filter(File::isDirectory)
                    .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
                    .limit(3)
                    .toList();
            toDelete.removeAll(listFiles);
            List<Path> toDeletePath = toDelete.stream().filter(File::isDirectory).map(File::toPath).toList();
            for (Path path : toDeletePath) {
                try (Stream<Path> temp = Files.walk(path)) {
                    temp.sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                }
            }
        }
    }
}











