package com.lzdk.monitoring.slack.message.service;

import java.io.IOException;

import com.lzdk.monitoring.slack.message.domain.Block;
import com.lzdk.monitoring.slack.message.domain.BlockList;
import com.lzdk.monitoring.slack.utils.SlackProperties;
import com.lzdk.monitoring.sonarqube.utils.SonarqubeProperties;
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

    private final SonarqubeProperties sonarqubeProperties;

    static void publishMessage(String channelId, String message) {
        var client = Slack.getInstance().methods();
        try {
            var result = client.chatPostMessage(r -> r
                .token(SlackProperties.getToken())
                .channel(channelId)
                .blocksAsString(message)
            );
            log.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }

    public void send(String targetId, Object componentKey) {
        if (SlackProperties.canUseDm()) {
            publishMessage(targetId, makeBlocks(componentKey.toString()));
        } else {
            publishMessage(SlackProperties.getChannelId(), makeMentionBlocks(targetId, componentKey.toString()));
        }
    }

    public void sendToAdmin(Object componentKey) {
        if (StringUtils.isEmpty(adminId)) {
            log.debug("Author not found in the Slack channel. : {} ", componentKey.toString());
        } else {
            publishMessage(adminId, makeBlocks(componentKey.toString()));
        }
    }

    private String makeBlocks(String componentKey) {
        BlockList blocks = BlockList.create(
            Block.createMarkdown(StringUtils.join(SlackProperties.getDeliveryMessage(), componentKey), sonarqubeProperties.getConsoleUrl())
        );
        return blocks.toJson();
    }

    private String makeMentionBlocks(String mentionId, String componentKey) {
        BlockList blocks = BlockList.create(
            Block.createMarkdown(StringUtils.join("<@" + mentionId + "> ", SlackProperties.getDeliveryMessage(), componentKey), sonarqubeProperties.getConsoleUrl())
        );
        return blocks.toJson();
    }
}
