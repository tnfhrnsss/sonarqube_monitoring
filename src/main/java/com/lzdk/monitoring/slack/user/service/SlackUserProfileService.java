package com.lzdk.monitoring.slack.user.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.slack.channel.service.SlackConversationService;
import com.lzdk.monitoring.slack.utils.SlackApiConfig;
import com.slack.api.Slack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackUserProfileService {
    private static final Map<String, String> profiles = new ConcurrentHashMap<>();

    private final SlackConversationService slackConversationService;

    private final SlackUserService slackUserService;

    static Map<String, String> findAll(List<String> members) {
        var client = Slack.getInstance().methods();

        members.forEach(user -> {
            try {
                var result =  client.usersProfileGet(r ->
                    r.token(SlackApiConfig.getToken()).user(user.trim())
                );
                profiles.put(user, result.getProfile().getEmail());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return profiles;
    }

    public Map<String, String> findAll() {
        String channelId = slackConversationService.find();
        List<String> users = slackUserService.findAll(channelId);
        return findAll(users);
    }
}
