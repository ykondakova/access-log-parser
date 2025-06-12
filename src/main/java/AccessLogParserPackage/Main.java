package AccessLogParserPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Statistics statistics = new Statistics();

        int numberOfFiles = 0;

        while (true) {
            System.out.println("Введите путь к файлу");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExist = file.exists();
            boolean isDirectory = file.isDirectory();

            if (!fileExist || isDirectory) {
                System.out.println("Указанный файл не существует или указанный путь является путём к папке");
                continue;
            }
            numberOfFiles++;
            System.out.println("Путь указан верно" + "\n" + "Это файл номер " + numberOfFiles);

            int countLines = 0;

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    countLines++;
                    if (line.length() > 1024) throw new LineTooLongException();
                    LogEntry logEntry = new LogEntry(line);
                    statistics.addEntry(logEntry);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Общее количество строк в файле: " + countLines);
            System.out.println("Средний объём трафика сайта за час: " + statistics.getTrafficRate());
            System.out.println("Адреса существующих страниц:" + "\n" + statistics.getAllExistPaths());
            System.out.println("Адреса несуществующих страниц:" + "\n" + statistics.getAllNotExistPaths());
            System.out.println("Статистика операционных систем пользователей сайта: " + statistics.getOsStatistics());
            System.out.println("Статистика браузеров пользователей сайта: " + statistics.getBrowserStatistics());

        }
    }
}
