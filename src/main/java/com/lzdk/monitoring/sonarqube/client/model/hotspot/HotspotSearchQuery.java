package com.lzdk.monitoring.sonarqube.client.model.hotspot;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class HotspotSearchQuery implements JsonSerializable {
    @Builder.Default
    private String projectKey = null;

    @Builder.Default
    private int p = 1;

    @Builder.Default
    private String status = "TO_REVIEW";

    @Builder.Default
    private boolean onlyMine = false;
}
