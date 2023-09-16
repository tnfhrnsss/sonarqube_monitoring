package com.lzdk.monitoring.slack.message.domain;

import java.util.ArrayList;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockList extends ArrayList implements JsonSerializable {

    public static BlockList addHeader(HeaderBlock headerBlock) {
        BlockList list = new BlockList();
        list.add(headerBlock);
        return list;
    }

    @Override
    public String toString() {
        return toJson();
    }

    public void addMentionBlock(MentionBlock block) {
        this.add(block);
    }

    public void addDmBlock(DmBlock block) {
        this.add(block);
    }
}
