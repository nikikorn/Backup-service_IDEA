package ru.nikikorn.backup;

import java.io.IOException;

public class BackupServiceApplication {

    public static void main(String[] args) throws IOException {
        BackupSettings backupSettings = new BackupSettings();
        Backup backup = new Backup(backupSettings);

        Backup.copy(backupSettings);







    }
}
