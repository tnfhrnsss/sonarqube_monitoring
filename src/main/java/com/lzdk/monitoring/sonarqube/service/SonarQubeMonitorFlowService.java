package com.lzdk.monitoring.sonarqube.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.slack.message.service.SlackSendMessageService;
import com.lzdk.monitoring.slack.user.service.SlackUserInfoService;
import com.lzdk.monitoring.sonarqube.author.sdo.AuthorSdo;
import com.lzdk.monitoring.sonarqube.author.service.SonarQubeAuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarQubeMonitorFlowService {
    private final SonarQubeIssueClientService sonarQubeIssueClientService;

    private final SonarQubeHotspotClientService sonarQubeHotspotClientService;

    private final SlackSendMessageService slackSendMessageService;

    private final SonarQubeAuthorService sonarQubeAuthorService;

    private final SlackUserInfoService slackUserInfoService;

    @Async("taskExecutor")
    @Scheduled(cron = "${jobs.cronSchedule:}")
    public void alert() {
        try {
            findIssue();
            findHotspot();
            findAllSlackUser();
            sendMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            destroy();
        }
    }

    private void findHotspot() {
        sonarQubeHotspotClientService.findAll()
            .forEach(hotspot -> sonarQubeAuthorService.enroll(AuthorSdo.create(hotspot.getProject(), hotspot.getAuthor())));
    }

    private void findIssue() {
        sonarQubeIssueClientService.findAll()
            .getIssues().forEach(issue -> sonarQubeAuthorService.enroll(AuthorSdo.create(issue.getProject(), issue.getAuthor())));
    }

    private void findAllSlackUser() {
        slackUserInfoService.findAll();
    }

    private void sendMessage() {
        Map<String, ConcurrentHashMap.KeySetView> targets = sonarQubeAuthorService.findAll();
        slackSendMessageService.message(targets);
    }

    private void destroy() {
        sonarQubeAuthorService.remove();
    }
}
