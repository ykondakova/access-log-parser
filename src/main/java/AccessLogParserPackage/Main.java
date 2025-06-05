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
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            int maxLength = linesLength.get(0);
            int minLength = linesLength.get(0);

            for (Integer i: linesLength) {
                if (i > maxLength) {
                    maxLength = i;
                }
                if (i < minLength) {
                    minLength = i;
                }
            }

            System.out.println("Общее количество строк в файле: " + linesLength.size());
            System.out.println("Длина самой длинной строки в файле: " + maxLength);
            System.out.println("Длина самой короткой строки в файле: " + minLength);

        }

    }
}
