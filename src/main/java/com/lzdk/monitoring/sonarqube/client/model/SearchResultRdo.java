package com.lzdk.monitoring.sonarqube.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private IssueList issues;

    @JsonIgnore
    public static SearchResultRdo EMPTY() {
        return new SearchResultRdo();
    }

    @JsonIgnore
    public int maxPage() {
        return Math.min((this.total + 99) / 100, this.p);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
