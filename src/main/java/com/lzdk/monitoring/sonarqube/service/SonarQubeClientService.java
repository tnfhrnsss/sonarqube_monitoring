package com.lzdk.monitoring.sonarqube.service;

import java.util.Arrays;
import java.util.List;

import com.lzdk.monitoring.sonarqube.client.SonarQubeClient;
import com.lzdk.monitoring.sonarqube.client.model.SearchQuery;
import com.lzdk.monitoring.sonarqube.client.model.SearchResultRdo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarQubeClientService {
    private final SonarQubeClient sonarQubeClient;

    @Value("${monitoring.sonarqube.component.keys:}")
    private List<String> componentKeys;

    public SearchResultRdo findAllIssues() {
        if (validate()) {
            return SearchResultRdo.EMPTY();
        }

        SearchResultRdo rdo = search(1);
        for (int i=1; i < rdo.maxPage(); i++) {
            rdo.getIssues().addAll(search(i).getIssues());
        }
        return rdo;
    }

    private SearchResultRdo search(int page) {
        return sonarQubeClient.search(
            SearchQuery.builder()
                .componentKeys(componentKeys)
                .p(page)
                .facets(Arrays.asList("severities", "types"))
                .build()
        );
    }

    private boolean validate() {
        return CollectionUtils.isEmpty(componentKeys);
    }
}
