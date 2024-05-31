package com.lzdk.monitoring.slack.message.service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.lzdk.monitoring.slack.message.domain.BlockList;
import com.lzdk.monitoring.slack.message.domain.DmBlock;
import com.lzdk.monitoring.slack.message.domain.HeaderBlock;
import com.lzdk.monitoring.slack.message.domain.MarkdownBlock;
import com.lzdk.monitoring.slack.message.domain.MentionBlock;
import com.lzdk.monitoring.slack.utils.SlackProperties;
import com.lzdk.monitoring.sonarqube.utils.SonarqubeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlackBlockService {
    private final SonarqubeProperties sonarqubeProperties;

    public String makeDmBlock(String componentKey) {
        BlockList blockList = BlockList.addHeader(HeaderBlock.create(SlackProperties.getDeliveryMessage()));
        blockList.addDmBlock(
            DmBlock.create(componentKey, sonarqubeProperties.getConsoleUrl())
        );
        return blockList.toJson();
    }

    public String makeMentionBlocks(Map<String, Map> targets) {
        BlockList blockList = BlockList.addHeader(HeaderBlock.create(SlackProperties.getDeliveryMessage()));
        blockList.addMentionBlock(
            MentionBlock.create(targets, sonarqubeProperties.getConsoleUrl())
        );
        return blockList.toJson();
    }

    public String makeChannelBlocks(Map<String, Map> targets) {
        BlockList blockList = BlockList.addHeader(HeaderBlock.create(SlackProperties.getDeliveryMessage()));
        blockList.addDmBlock(DmBlock.create("Please check your code.", sonarqubeProperties.getConsoleUrl()));
        blockList.addChannelBlock(
            targets.entrySet()
                .stream()
                .map(k -> MarkdownBlock.create(enrichmentMessage(k.getKey(), k.getValue().keySet())))
                .collect(Collectors.toList())
        );
        return blockList.toJson();
    }

    private String enrichmentMessage(String userId, Set set) {
        String message = set.toString();
        if (StringUtils.isNotEmpty(userId)) {
            return StringUtils.join("<@", userId, ">", " ", message);
        }
        return message;
    }
}
