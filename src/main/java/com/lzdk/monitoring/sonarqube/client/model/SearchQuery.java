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
    @Builder.Default
    private List<String> componentKeys = Collections.emptyList();

    @Builder.Default
    private int p = 1;

    @Builder.Default
    private String s = "FILE_LINE";

    @Builder.Default
    private boolean resolved = false;

    @Builder.Default
    private String types = "CODE_SMELL";

    @Builder.Default
    private List<String> facets = Collections.emptyList();
}
