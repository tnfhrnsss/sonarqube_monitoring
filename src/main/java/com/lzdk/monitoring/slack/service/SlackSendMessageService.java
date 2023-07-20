package com.lzdk.monitoring.slack.service;

import java.io.IOException;
import java.util.List;

import com.lzdk.monitoring.slack.user.service.SlackUserProfileService;
import com.lzdk.monitoring.slack.user.service.SlackUserService;
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
    private final SlackConversationService slackConversationService;

    private final SlackUserService slackUserService;

    private final SlackUserProfileService slackUserProfileService;

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

    public void send(String message) {
        String channelId = slackConversationService.find();

        // 1. 해당 채널 id에 연결된 user리스트 가져오고, 캐시에 저장한 후

        List<String> users = slackUserService.findAll(channelId);

        // 2. user리스트들의 이메일 주소를 또 가져오고

        slackUserProfileService.findAll(users);
        // 3. 매칭 시킨다. 타켓에 있는지. 없으면, 따로 모아서 전체 공지하고...

        publishMessage(users.get(0), message);
    }
}
