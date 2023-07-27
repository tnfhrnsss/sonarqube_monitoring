package com.lzdk.monitoring.sonarqube.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        Map<String, ConcurrentHashMap.KeySetView> targets = sonarQubeAuthorService.findAll();
        Map<String, String> slackUserProfiles = slackUserInfoService.findAll();

        try {
            targets.forEach((k, v) -> {
            if (slackUserProfiles.containsKey(k)) {
                slackSendMessageService.send(slackUserProfiles.get(k).toString(), v.getMap().keySet());
            } else {
                slackSendMessageService.sendToAdmin(v.getMap().keySet());
            }});
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void destroy() {
        sonarQubeAuthorService.remove();
    }
}
