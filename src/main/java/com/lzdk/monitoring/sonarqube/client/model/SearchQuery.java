package com.lzdk.monitoring.sonarqube.client.model;

import java.util.Collections;
import java.util.List;

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
public class SearchQuery implements JsonSerializable {
    //?componentKeys=core-asset-espresso&s=FILE_LINE&resolved=false&types=CODE_SMELL
    // &ps=100&facets=severities%2Ctypes&timeZone=Asia%2FSeoul

    @Builder.Default
    private List<String> componentKeys = Collections.emptyList();

    private String s = "FILE_LINE";

    private boolean resolved = false;

    private String types = "CODE_SMELL";

    private List<String> facets = Collections.emptyList();
}
