package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:${env}.properties"})
public interface WebConfig extends Config {

    @Key("browserName")
    String getBrowserName();

    @Key("browserVersion")
    String getBrowserVersion();

    @Key("baseUrl")
    String getBaseUrl();

    @Key("browserSize")
    String getBrowserSize();

    @Key("isRemote")
    Boolean isRemote();

    @Key("remoteUrl")
    String getRemoteUrl();

    @Key("pageLoadTimeout")
    Long getPageLoadTimeout();

    @Key("timeout")
    Long getTimeout();

    @Key("headless")
    Boolean isHeadless();
}
