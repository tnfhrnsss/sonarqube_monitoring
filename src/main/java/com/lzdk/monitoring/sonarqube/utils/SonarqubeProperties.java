package com.lzdk.monitoring.sonarqube.utils;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("monitoring.sonarqube")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SonarqubeProperties {
    private String consoleUrl;

    private String apiUrl;

    private List<String> componentKeys;

    public void setConsoleUrl(String consoleUrl) {
        this.consoleUrl = consoleUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setComponentKeys(List<String> componentKeys) {
        this.componentKeys = componentKeys;
    }

    public String getComponents() {
        return this.getComponentKeys().stream().collect(Collectors.joining(","));
    }
}
