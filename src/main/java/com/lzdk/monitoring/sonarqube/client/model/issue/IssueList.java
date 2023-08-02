package com.lzdk.monitoring.sonarqube.client.model.issue;

import java.util.ArrayList;
import java.util.Collection;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueList extends ArrayList<Issue> implements JsonSerializable {
    public IssueList(Collection<Issue> c) {
        super(c);
    }
}
