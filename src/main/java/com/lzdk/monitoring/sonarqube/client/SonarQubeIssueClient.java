package com.lzdk.monitoring.sonarqube.client;

import com.lzdk.monitoring.sonarqube.client.model.issue.IssueSearchQuery;
import com.lzdk.monitoring.sonarqube.client.model.issue.IssueSearchResultRdo;
import com.lzdk.monitoring.utils.feign.FeignConfiguration;
import com.lzdk.monitoring.utils.feign.FeignLoggerLevelConfiguration;
import com.lzdk.monitoring.utils.feign.FeignRetryConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
    contextId = "com.lzdk.monitoring.sonarqube.client.SonarQubeIssueClient",
    url = "${monitoring.sonarqube.apiUrl}",
    name = "sonarqube",
    configuration = {FeignConfiguration.class, FeignRetryConfiguration.class, FeignLoggerLevelConfiguration.class},
    primary = false
)
public interface SonarQubeIssueClient {
    @GetMapping("issues/search")
    IssueSearchResultRdo search(@SpringQueryMap IssueSearchQuery issueSearchQuery);
}
