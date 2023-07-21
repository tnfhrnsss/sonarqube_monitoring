package com.lzdk.monitoring.sonarqube.service;

import java.util.Map;

import com.lzdk.monitoring.slack.service.SlackSendMessageService;
import com.lzdk.monitoring.slack.user.service.SlackUserProfileService;
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

    private final SlackUserProfileService slackUserProfileService;

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
        Map targets = sonarQubeAuthorService.findAll();
        Map<String, String> slackUserProfiles = slackUserProfileService.findAll();

        targets.forEach((k, v) -> slackUserProfiles.entrySet().forEach(profile -> {
            if (profile.getValue().equals(k)) {
                slackSendMessageService.send(profile.getKey(), v.toString());
            } else {
                log.debug("Author not found in the Slack channel. : ", v.toString());
            }
            slackSendMessageService.send(profile.getKey(), "Code smells have been detected in SonarQube. Please fix them. component : " + v.toString());
        }));
    }

    private void destroy() {
        sonarQubeAuthorService.remove();
    }
}
