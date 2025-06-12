package AccessLogParserPackage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {

    long totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;
    HashSet<String> existPaths;
    HashSet<String> notExistPaths;
    HashMap<String, Integer> osCount;
    HashMap<String, Integer> browserCount;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
        this.existPaths = new HashSet<>();
        this.notExistPaths = new HashSet<>();
        this.osCount = new HashMap<>();
        this.browserCount = new HashMap<>();
    }

    public void addEntry(LogEntry logEntry) {

        this.totalTraffic += logEntry.responseSize;

        if (this.minTime == null || this.minTime.isAfter(logEntry.getTime())) {
            this.minTime = logEntry.getTime();
        }
        if (this.maxTime == null || this.maxTime.isBefore(logEntry.getTime())) {
            this.maxTime = logEntry.getTime();
        }
        if (logEntry.getResponseCode() == 200) {
            this.existPaths.add(logEntry.getPath());
        }
        if (logEntry.getResponseCode() == 404) {
            this.notExistPaths.add(logEntry.getPath());
        }
        if (this.osCount.containsKey(logEntry.getUserAgent().getOsType().toString())) {
            this.osCount.put(logEntry.getUserAgent().getOsType().toString(), this.osCount.get(logEntry.getUserAgent().getOsType().toString()) + 1);
        } else {
            this.osCount.put(logEntry.getUserAgent().getOsType().toString(), 1);
        }
        if (this.browserCount.containsKey(logEntry.getUserAgent().getBrowser().toString())) {
            this.browserCount.put(logEntry.getUserAgent().getBrowser().toString(), this.browserCount.get(logEntry.getUserAgent().getBrowser().toString()) + 1);
        } else {
            this.browserCount.put(logEntry.getUserAgent().getBrowser().toString(), 1);
        }
    }

    public long getTrafficRate() {
        long hourDifference = Duration.between(this.minTime, this.maxTime).toHours();
        return this.totalTraffic / hourDifference;
    }

    public HashSet<String> getAllExistPaths() {
        return this.existPaths;
    }

    public HashSet<String> getAllNotExistPaths() {
        return this.notExistPaths;
    }

    public HashMap<String, Double> getOsStatistics() {
        int allOsCount = 0;
        for (Integer i : this.osCount.values()) {
            allOsCount += i;
        }
        HashMap<String, Double> osStatistics = new HashMap<>();
        for (String s : this.osCount.keySet()) {
            osStatistics.put(s, (double) this.osCount.get(s) / allOsCount);
        }
        return osStatistics;
    }

    public HashMap<String, Double> getBrowserStatistics() {
        int allBrowserCount = 0;
        for (Integer i : this.browserCount.values()) {
            allBrowserCount += i;
        }
        HashMap<String, Double> browserStatistics = new HashMap<>();
        for (String s : this.browserCount.keySet()) {
            browserStatistics.put(s, (double) this.browserCount.get(s) / allBrowserCount);
        }
        return browserStatistics;
    }
}
