package com.iwmnetwork.aqtos.identify.demo.config.enviroment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "fido")
@Component
@Data
public class Fido2EnvironmentConfiguration {
    private String rpId;
    private String login_finish_url;
    private String login_url;
    private String type_create;
    private String type_get;
    private String origin;
}
