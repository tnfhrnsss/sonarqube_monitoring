package com.lzdk.monitoring.slack.message.service;

import java.io.IOException;

import com.lzdk.monitoring.slack.utils.SlackApiConfig;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackSendMessageService {
    static void publishMessage(String channelId, String message) {
        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage(r -> r
                .token(SlackApiConfig.getToken())
                .channel(channelId)
                .text(message)
            );
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }

    public void send(String targetId, String message) {
        publishMessage(targetId, message);
    }
}
