package com.lzdk.monitoring.sonarqube.service;

import java.util.Arrays;

import com.lzdk.monitoring.sonarqube.client.SonarQubeIssueClient;
import com.lzdk.monitoring.sonarqube.client.model.issue.IssueSearchQuery;
import com.lzdk.monitoring.sonarqube.client.model.issue.IssueSearchResultRdo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarQubeIssueClientService {
    private final SonarQubeIssueClient sonarQubeIssueClient;

    @Value("${monitoring.sonarqube.component.keys:}")
    private String componentKeys;

    public IssueSearchResultRdo findAll() {
        if (validate()) {
            return IssueSearchResultRdo.EMPTY();
        }

        IssueSearchResultRdo rdo = search(1);
        for (int i=1; i < rdo.maxPage(); i++) {
            rdo.getIssues().addAll(search(i).getIssues());
        }
        return rdo;
    }

    private IssueSearchResultRdo search(int page) {
        return sonarQubeIssueClient.search(
            IssueSearchQuery.builder()
                .componentKeys(componentKeys)
                .p(page)
                .facets(Arrays.asList("severities", "types"))
                .build()
        );
    }

    private boolean validate() {
        return StringUtils.isEmpty(componentKeys);
    }
}
