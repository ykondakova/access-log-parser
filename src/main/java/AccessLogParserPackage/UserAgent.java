package AccessLogParserPackage;

public class UserAgent {

    final OsType osType;
    final Browser browser;
    final String wholeUserAgent;

    public OsType getOsType() {
        return osType;
    }

    public Browser getBrowser() {
        return browser;
    }

    public UserAgent(String userAgent) {

        if (userAgent.contains("Windows")) {
            this.osType = OsType.WINDOWS;
        } else if (userAgent.contains("Linux")) {
            this.osType = OsType.LINUX;
        } else if (userAgent.contains("Mac OS")) {
            this.osType = OsType.MAC_OS;
        } else this.osType = OsType.UNKNOWN;

        if (userAgent.contains("Firefox/")) {
            this.browser = Browser.FIREFOX;
        } else if (userAgent.contains("Edg/")) {
            this.browser = Browser.EDGE;
        } else if (userAgent.contains("OPR/")) {
            this.browser = Browser.OPERA;
        } else if (userAgent.contains("Chrome/")) {
            this.browser = Browser.CHROME;
        } else {
            this.browser = Browser.OTHER;
        }
        this.wholeUserAgent = userAgent;
    }
    public boolean isBot () {
        return this.wholeUserAgent.contains("bot");
    }

}
