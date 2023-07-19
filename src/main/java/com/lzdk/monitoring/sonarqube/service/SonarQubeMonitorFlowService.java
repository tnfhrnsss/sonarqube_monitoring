package com.lzdk.monitoring.sonarqube.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.slack.service.SlackSendMessageService;
import com.lzdk.monitoring.sonarqube.client.model.Issue;
import com.lzdk.monitoring.sonarqube.client.model.SearchResultRdo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarQubeMonitorFlowService {
    private final SonarQubeClientService sonarQubeClientService;

    private final SlackSendMessageService slackSendMessageService;

    private static final Map<String, ConcurrentHashMap.KeySetView> targets = new ConcurrentHashMap<>();

    public void alert() {
        SearchResultRdo resultRdo = sonarQubeClientService.findAllIssues();

        System.out.println(resultRdo.toString());

        enrichment(resultRdo.getIssues());

        System.out.println("===============");
        System.out.println(targets.size());
        targets.forEach((a, b) -> {
            System.out.println(a.toString() + "---"+ b.toString());
        });

        sendMessage();
    }

    private void enrichment(List<Issue> issues) {
        issues.forEach(issue -> {
            targets.computeIfAbsent(issue.getAuthor(), k -> ConcurrentHashMap.newKeySet()).add(issue.getProject());
        });
    }

    private void sendMessage() {
        slackSendMessageService.send("exist code-smell! : " + targets.toString());
    }
}
