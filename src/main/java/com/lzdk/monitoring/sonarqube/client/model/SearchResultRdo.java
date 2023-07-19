package com.lzdk.monitoring.sonarqube.client.model;

import java.util.List;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResultRdo implements JsonSerializable {
    private int total;

    private int p;

    private int ps;

    private List<Issue> issues;

    public static SearchResultRdo EMPTY() {
        return new SearchResultRdo();
    }

    @Override
    public String toString() {
        return toJson();
    }
}
