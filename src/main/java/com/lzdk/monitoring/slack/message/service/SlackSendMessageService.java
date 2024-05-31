package com.lzdk.monitoring.slack.message.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.slack.user.service.SlackUserDeepMatchingService;
import com.lzdk.monitoring.slack.utils.SlackProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackSendMessageService {
    private final SlackMessageService slackMessageService;

    private final SlackBlockService slackBlockService;

    private final SlackUserDeepMatchingService slackUserDeepMatchingService;

    private void send(Map<String, Map> targets) {
        if (SlackProperties.canUseDm()) {
            targets.entrySet()
                .forEach(k -> slackMessageService.publish(k.getKey(), slackBlockService.makeDmBlock(k.getValue().toString())));
        } else {
            slackMessageService.publish(SlackProperties.getChannelId(), slackBlockService.makeChannelBlocks(targets));
        }
    }

    public void message(Map<String, ConcurrentHashMap.KeySetView> targets) {
        Map<String, Map> sendTargets = new HashMap<>();

        try {
            targets.forEach((k, v) -> {
                String slackUserId = slackUserDeepMatchingService.find(k);
                if (StringUtils.isNotEmpty(slackUserId)) {
                    sendTargets.put(slackUserId, v.getMap().keySet().getMap());
                } else {
                    sendTargets.put(k, v.getMap().keySet().getMap());
                }
            });

            if (!CollectionUtils.isEmpty(sendTargets)) {
                send(sendTargets);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
