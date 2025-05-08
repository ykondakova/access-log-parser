package AccessLogParserPackage;
import java.io.File;
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
        }
    }
}
