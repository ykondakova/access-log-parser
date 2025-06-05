package AccessLogParserPackage;

public class LineTooLongException extends Exception{

    public LineTooLongException () {
        super("В файле присутствует строка длиннее 1024 символов");
    }

}
