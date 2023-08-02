package com.lzdk.monitoring.sonarqube.client.model.hotspot;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotspot implements JsonSerializable {
    private String project;

    private String author;

    @Override
    public String toString() {
        return toJson();
    }
}
