package com.lzdk.monitoring.sonarqube.client.model.issue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueSearchResultRdo implements JsonSerializable {
    private int total;

    private int p;

    private int ps;

    private IssueList issues;

    @JsonIgnore
    public static IssueSearchResultRdo EMPTY() {
        return new IssueSearchResultRdo();
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
