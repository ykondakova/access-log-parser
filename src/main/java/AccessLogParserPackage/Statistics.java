package AccessLogParserPackage;

import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {

    long totalTraffic;
    LocalDateTime minTime;
    LocalDateTime maxTime;

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = null;
        this.maxTime = null;
    }

    public void addEntry(LogEntry logEntry) {

        this.totalTraffic += logEntry.responseSize;

        if (this.minTime == null || this.minTime.isAfter(logEntry.getTime())) {
            this.minTime = logEntry.getTime();
        }
        if (this.maxTime == null || this.maxTime.isBefore(logEntry.getTime())) {
            this.maxTime = logEntry.getTime();
        }
    }

    public long getTrafficRate() {
        long hourDifference = Duration.between(this.minTime, this.maxTime).toHours();
        return this.totalTraffic / hourDifference;
    }

}
