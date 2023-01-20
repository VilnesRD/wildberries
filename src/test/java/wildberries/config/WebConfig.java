package wildberries.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:${env}.properties"
})
public interface WebConfig extends Config {

    @Key("base_url")
    String getBaseUrl();

    @Key("browser")
    String getBrowser();

    @Key("browser_ver")
    String getBrowserVersion();

    @Key("screen_resolution")
    String getScreenResolution();

    @Key("isRemote")
    Boolean IsRemote();

    @Key("remote")
    String getRemote();
}