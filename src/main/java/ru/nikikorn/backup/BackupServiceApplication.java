package ru.nikikorn.backup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Properties;

public class BackupServiceApplication {

    public static void main(String[] args) throws IOException {
        BackupSettings backupSettings = new BackupSettings();
        backupSettings.start();

        /*BackupCopying backupCopying = new BackupCopying();
        backupCopying.copy();*/







    }
}
