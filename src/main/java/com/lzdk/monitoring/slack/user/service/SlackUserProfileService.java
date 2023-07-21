package com.lzdk.monitoring.slack.user.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.slack.utils.SlackApiConfig;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackUserProfileService {
    private static final Map<String, String> profiles = new ConcurrentHashMap<>();

    public Map<String, String> findAll(List<String> members) {
        var users = Slack.getInstance().methods();

        members.forEach(user -> {
            try {
                var result =  users.usersProfileGet(r ->
                        r.token(SlackApiConfig.getToken())
                                .user(user.trim())
                );

                profiles.put(user, result.getProfile().getEmail());
                System.out.println(result.getProfile().getEmail());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SlackApiException e) {
                throw new RuntimeException(e);
            }
        });

        return profiles;
    }
}
