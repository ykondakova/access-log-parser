package AccessLogParserPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int numberOfFiles = 0;
        int yandexBotCount = 0;
        int googleBotCount = 0;

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

            List<Integer> linesLength = new ArrayList<>();

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.length() > 1024) throw new LineTooLongException();
                    linesLength.add(line.length());
                    String[] parts = line.split(" ");
                    String ip = parts[0];
                    String missingProp1 = parts[1];
                    String missingProp2 = parts[2];
                    String dateTime = parts[3] + parts[4];
                    String url = parts[5] + " " + parts[6] + " " + parts[7];
                    String httpCode = parts[8];
                    String dataSize = parts[9];
                    String referer = parts[10];
                    String userAgent = "";
                    int i = 11;
                    for (; i < parts.length - 1; i++) {
                        userAgent += parts[i] + " ";
                    }
                    userAgent += parts[i];

                    String firstBracketsUA = "";

                    int startBracket = line.indexOf('(');
                    int endBracket = line.indexOf(')', startBracket);

                    if (startBracket != -1 && endBracket != -1 && startBracket < endBracket) {
                        firstBracketsUA = line.substring(startBracket + 1, endBracket);
                    }

                    String fragment = "";

                    String[] partsFirstBracketsUA = firstBracketsUA.split(";");
                    if (partsFirstBracketsUA.length >= 2) {
                        fragment = partsFirstBracketsUA[1];
                    }

                    String programSender = fragment.replace(" ", "");

                    int endSlash = programSender.indexOf('/');

                    if (endSlash != -1) {
                        programSender = programSender.substring(0, endSlash);
                    }


                    if (programSender.equals("YandexBot")) {
                        yandexBotCount++;
                    }
                    if (programSender.equals("Googlebot")) {
                        googleBotCount++;
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Общее количество строк в файле: " + linesLength.size());
            System.out.println("Доля запросов от YandexBot: " + (double)yandexBotCount/linesLength.size());
            System.out.println("Доля запросов от Googlebot: " + (double)googleBotCount/linesLength.size());

        }
    }
}
