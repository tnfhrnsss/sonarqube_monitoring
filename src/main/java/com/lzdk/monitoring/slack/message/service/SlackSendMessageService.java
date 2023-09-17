package com.lzdk.monitoring.slack.message.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.slack.utils.SlackProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackSendMessageService {
    @Value("${monitoring.slack.admin.id:}")
    private String adminId;

    private final SlackMessageService slackMessageService;

    private final SlackBlockService slackBlockService;

    private void send(Map<String, Map> targets) {
        if (SlackProperties.canUseDm()) {
            targets.entrySet()
                .forEach(k -> {
                    slackMessageService.publish(k.getKey(), slackBlockService.makeDmBlock(k.getValue().toString()));
                });
        } else {
            slackMessageService.publish(SlackProperties.getChannelId(), slackBlockService.makeChannelBlocks(targets));
        }
    }

    private void sendToAdmin(Object componentKey) {
        if (StringUtils.isEmpty(adminId)) {
            log.debug("Author not found in the Slack channel. : {} ", componentKey.toString());
        } else {
            slackMessageService.publish(adminId, slackBlockService.makeDmBlock(componentKey.toString()));
        }
    }

    public void message(Map<String, ConcurrentHashMap.KeySetView> targets, Map<String, String> slackUserProfiles) {
        Map<String, Map> sendTargets = new HashMap<>();

        try {
            targets.forEach((k, v) -> {
                if (slackUserProfiles.containsKey(k)) {
                    sendTargets.put(slackUserProfiles.get(k), v.getMap().keySet().getMap());
                } else {
                    sendToAdmin(v.getMap().keySet());
                }});

            if (!CollectionUtils.isEmpty(sendTargets)) {
                send(sendTargets);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
