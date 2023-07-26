package com.lzdk.monitoring.sonarqube.client;

import com.lzdk.monitoring.sonarqube.client.model.SearchQuery;
import com.lzdk.monitoring.sonarqube.client.model.SearchResultRdo;
import com.lzdk.monitoring.utils.feign.FeignConfiguration;
import com.lzdk.monitoring.utils.feign.FeignLoggerLevelConfiguration;
import com.lzdk.monitoring.utils.feign.FeignRetryConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    contextId = "com.lzdk.monitoring.sonarqube.client.SonarQubeClient",
    url = "${monitoring.sonarqube.api.url}",
    name = "sonarqube",
    configuration = {FeignConfiguration.class, FeignRetryConfiguration.class, FeignLoggerLevelConfiguration.class},
    primary = false
)
public interface SonarQubeClient {
    @GetMapping("issues/search")
    SearchResultRdo search(@SpringQueryMap SearchQuery searchQuery);
}
