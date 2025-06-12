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

    HashMap<String, Integer> osCount;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
        this.existPaths = new HashSet<>();
        this.osCount = new HashMap<>();
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
        if (this.osCount.containsKey(logEntry.getUserAgent().getOsType().toString())) {
            this.osCount.put(logEntry.getUserAgent().getOsType().toString(), this.osCount.get(logEntry.getUserAgent().getOsType().toString()) + 1);
        } else {
            this.osCount.put(logEntry.getUserAgent().getOsType().toString(), 1);
        }
    }

    public long getTrafficRate() {
        long hourDifference = Duration.between(this.minTime, this.maxTime).toHours();
        return this.totalTraffic / hourDifference;
    }

    public HashSet<String> getAllExistPaths() {
        return this.existPaths;
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
}
