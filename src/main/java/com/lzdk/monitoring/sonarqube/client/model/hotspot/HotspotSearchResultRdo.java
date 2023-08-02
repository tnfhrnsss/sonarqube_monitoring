package com.lzdk.monitoring.sonarqube.client.model.hotspot;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotspotSearchResultRdo implements JsonSerializable {
    private Paging paging;

    private HotspotList hotspots;

    @Override
    public String toString() {
        return toJson();
    }

    @JsonIgnore
    public boolean exist() {
        return this.paging.getTotal() > 0;
    }
}
