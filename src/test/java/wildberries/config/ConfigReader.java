package config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigReader {

    private static final WebConfig webConfig = ConfigFactory.create(WebConfig.class, System.getProperties());

    public static WebConfig read() {
        return webConfig;
    }
}
