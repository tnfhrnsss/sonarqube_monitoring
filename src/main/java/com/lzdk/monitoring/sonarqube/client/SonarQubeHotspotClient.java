package com.lzdk.monitoring.sonarqube.client;

import com.lzdk.monitoring.sonarqube.client.model.hotspot.HotspotSearchQuery;
import com.lzdk.monitoring.sonarqube.client.model.hotspot.HotspotSearchResultRdo;
import com.lzdk.monitoring.utils.feign.FeignConfiguration;
import com.lzdk.monitoring.utils.feign.FeignLoggerLevelConfiguration;
import com.lzdk.monitoring.utils.feign.FeignRetryConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    contextId = "com.lzdk.monitoring.sonarqube.client.SonarQubeHotspotClient",
    url = "${monitoring.sonarqube.apiUrl}",
    name = "sonarqube",
    configuration = {FeignConfiguration.class, FeignRetryConfiguration.class, FeignLoggerLevelConfiguration.class},
    primary = false
)
public interface SonarQubeHotspotClient {
    @GetMapping("hotspots/search")
    HotspotSearchResultRdo search(@SpringQueryMap HotspotSearchQuery hotspotSearchQuery);
}
