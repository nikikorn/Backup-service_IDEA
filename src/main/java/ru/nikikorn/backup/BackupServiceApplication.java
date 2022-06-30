package ru.nikikorn.backup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BackupServiceApplication {
    private static String targetDirectory; //путь до директории, которую нужно забекапить (по умолчанию: там, где лежит jar)
    private static String backupDirectory; // директория, в которую скидываются данные для backup (по умолчанию: там, где лежит jar + /backup)
    private static boolean includeDirectory;  //флаг, показывающий, что нужно копировать внутренние директории и файлы (по умолчанию: false)
    private static int backupCicleSize; //- кол-во циклов создания backup директорий (по умолчанию: 3)


    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("Config.txt")) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            targetDirectory = "/Users/nikikorn/IdeaProjects/Backup-service/";
            backupDirectory = "/Users/nikikorn/IdeaProjects/Backup-service/backup/";
            includeDirectory = false;
            backupCicleSize = 3;
        }
        

        /*try {
            targetDirectory = properties.getProperty("targetDirectory");
            backupDirectory = properties.getProperty("backupDirectory");
            includeDirectory = Boolean.parseBoolean(properties.getProperty("includeDirectory"));
            backupCicleSize = Integer.parseInt(properties.getProperty("backupCicleSize"));
        } catch (NumberFormatException e) {
            System.out.println("tyt");
        }*/


    }
}
