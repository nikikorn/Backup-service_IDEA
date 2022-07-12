package ru.nikikorn.backup;

import java.io.IOException;

public class BackupServiceApplication {

    public static void main(String[] args) {
        BackupSettings backupSettings = new BackupSettings();
        try {
            Backup.start(backupSettings);
        } catch (IOException e) {
            System.out.println("Error appeared."+ e.getMessage());
        }
    }
}
