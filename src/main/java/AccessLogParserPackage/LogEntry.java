package AccessLogParserPackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {

    final String ipAddr;
    final LocalDateTime time;
    final HttpMethod method;
    final String path;
    final int responseCode;
    final int responseSize;
    final String referer;
    final UserAgent userAgent;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);

    public LogEntry (String logLine) {
        String[] parts = logLine.split(" ");
        this.ipAddr = parts[0];
        this.time = LocalDateTime.parse(parts[3].substring(1), formatter);
        this.method = HttpMethod.valueOf(parts[5].substring(1));
        this.path = (parts[6] + " " + parts[7]).substring(0, (parts[6] + " " + parts[7]).length()-1);
        this.responseCode = Integer.parseInt(parts[8]);
        this.responseSize = Integer.parseInt(parts[9]);
        this.referer = parts[10].substring(1, parts[10].length()-1);
        String result = "";
        int i = 11;
        for (; i < parts.length - 1; i++) {
            result+= parts[i] + " ";
        }
        result += parts[i];
        this.userAgent = new UserAgent(result.substring(1, result.length()-1));
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

}
