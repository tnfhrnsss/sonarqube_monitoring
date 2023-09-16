package com.lzdk.monitoring.slack.message.service;

import java.util.Map;

import com.lzdk.monitoring.slack.message.domain.BlockList;
import com.lzdk.monitoring.slack.message.domain.DmBlock;
import com.lzdk.monitoring.slack.message.domain.HeaderBlock;
import com.lzdk.monitoring.slack.message.domain.MentionBlock;
import com.lzdk.monitoring.slack.utils.SlackProperties;
import com.lzdk.monitoring.sonarqube.utils.SonarqubeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
