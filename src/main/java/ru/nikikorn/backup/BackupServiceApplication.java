package ru.nikikorn.backup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BackupServiceApplication {



    public static void main(String[] args) throws IOException {
        BackupSettings backupSettings = new BackupSettings();
        backupSettings.start();

    }
}
