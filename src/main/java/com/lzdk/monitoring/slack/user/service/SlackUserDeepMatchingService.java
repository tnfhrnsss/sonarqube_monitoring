package com.lzdk.monitoring.slack.user.service;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackUserDeepMatchingService {
    private final SlackUserInfoService slackUserInfoService;

    public String find(String email) {
        Map<String, String> userProfiles = slackUserInfoService.getUserProfiles();
        if (userProfiles.containsKey(email)) {
            return userProfiles.get(email);
        }

        for (Map.Entry<String, String> k : userProfiles.entrySet()) {
            String emailId = extractIdFromEmail(k.getKey());
            if (email.contains(emailId)) {
                return k.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    private static String extractIdFromEmail(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex != -1) {
            return email.substring(0, atIndex);
        }
        return email;
    }
}
