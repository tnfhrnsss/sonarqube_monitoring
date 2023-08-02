package com.lzdk.monitoring.sonarqube.client.model.hotspot;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Paging implements JsonSerializable {
    private int pageIndex;

    private int pageSize;

    private int total;

    @Override
    public String toString() {
        return toJson();
    }
}
