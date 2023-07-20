package com.lzdk.monitoring.slack.user.service;

import java.io.IOException;
import java.util.List;

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
    public void findAll(List<String> members) {
        var users = Slack.getInstance().methods();

        members.forEach(user -> {
            try {
                var result =  users.usersProfileGet(r ->
                        r.token(SlackApiConfig.getToken())
                                .user(user.trim())
                );

                System.out.println(result.getProfile().getEmail());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SlackApiException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
