package com.lzdk.monitoring.sonarqube.client.model.issue;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue implements JsonSerializable {
    private String project;

    private String status;

    private String author;

    private String type;

    @Override
    public String toString() {
        return toJson();
    }
}
