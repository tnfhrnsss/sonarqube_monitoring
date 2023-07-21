package com.lzdk.monitoring.sonarqube.service;

import com.lzdk.monitoring.slack.service.SlackSendMessageService;
import com.lzdk.monitoring.sonarqube.author.service.SonarQubeAuthorService;
import com.lzdk.monitoring.sonarqube.client.model.SearchResultRdo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarQubeMonitorFlowService {
    private final SonarQubeClientService sonarQubeClientService;

    private final SlackSendMessageService slackSendMessageService;

    private final SonarQubeAuthorService sonarQubeAuthorService;

    public void alert() {
        findIssue();
        sendMessage();
    }

    private void findIssue() {
        SearchResultRdo resultRdo = sonarQubeClientService.findAllIssues();

        log.debug(resultRdo.toString());

        resultRdo.getIssues().forEach(issue -> sonarQubeAuthorService.enroll(issue));
    }

    private void sendMessage() {
        slackSendMessageService.send(sonarQubeAuthorService.findAll());
    }
}
