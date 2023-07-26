package com.lzdk.monitoring.sonarqube.service;

import java.util.Map;

import com.lzdk.monitoring.slack.message.service.SlackSendMessageService;
import com.lzdk.monitoring.slack.user.service.SlackUserInfoService;
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

    private final SlackUserInfoService slackUserInfoService;

    public void alert() {
        try {
            findIssue();
            sendMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            destroy();
        }
    }

    private void findIssue() {
        SearchResultRdo resultRdo = sonarQubeClientService.findAllIssues();
        log.debug(resultRdo.toString());
        resultRdo.getIssues().forEach(issue -> sonarQubeAuthorService.enroll(issue));
    }

    private void sendMessage() {
        Map<String, String> targets = sonarQubeAuthorService.findAll();
        Map<String, String> slackUserProfiles = slackUserInfoService.findAll();

        targets.forEach((k, v) -> {
            if (slackUserProfiles.containsKey(k)) {
                slackSendMessageService.send(slackUserProfiles.get(k).toString(), "Code smells have been detected in SonarQube. Please fix them. component : " + v.toString());
            } else {
                log.debug("Author not found in the Slack channel. : {} ", v.toString());
            }
        });
    }

    private void destroy() {
        sonarQubeAuthorService.remove();
    }
}
