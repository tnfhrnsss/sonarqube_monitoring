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
public class SlackUserService {

    public List findAll(String channelId) {
        var users = Slack.getInstance().methods();

        try {
            var result =  users.conversationsMembers(r ->
                    r.token(SlackApiConfig.getToken()).channel(channelId)
            );

            for (String user : result.getMembers()) {
                System.out.println(user);
            }
            return result.getMembers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SlackApiException e) {
            throw new RuntimeException(e);
        }
    }
}
