package com.lzdk.monitoring.slack.message.service;

import java.io.IOException;

import com.lzdk.monitoring.slack.utils.SlackApiConfig;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackSendMessageService {
    @Value("${monitoring.slack.admin.id:}")
    private String adminId;

    private static final String PUSH_MESSAGE = "Code smells or Hotspots have been detected in SonarQube. Please fix them. component : ";

    static void publishMessage(String channelId, String message) {
        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage(r -> r
                .token(SlackApiConfig.getToken())
                .channel(channelId)
                .text(StringUtils.join(PUSH_MESSAGE, message))
            );
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }

    public void send(String targetId, Object componentKey) {
        publishMessage(targetId, componentKey.toString());
    }

    public void sendToAdmin(Object componentKey) {
        if (StringUtils.isEmpty(adminId)) {
            log.debug("Author not found in the Slack channel. : {} ", componentKey.toString());
        } else {
            publishMessage(adminId, componentKey.toString());
        }
    }
}
