package com.lzdk.monitoring.sonarqube.client.model.hotspot;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HotspotList extends ArrayList<Hotspot> implements JsonSerializable {
    public HotspotList(Collection<Hotspot> c) {
        super(c);
    }

    @JsonIgnore
    public void combine(HotspotList hotspots) {
        this.addAll(hotspots);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
