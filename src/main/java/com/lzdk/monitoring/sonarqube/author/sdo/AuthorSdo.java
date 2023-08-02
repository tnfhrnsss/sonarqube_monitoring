package com.lzdk.monitoring.sonarqube.author.sdo;

import com.lzdk.monitoring.utils.json.JsonSerializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorSdo implements JsonSerializable {
    private String project;

    private String author;

    public static AuthorSdo create(String project, String author) {
        return new AuthorSdo(project, author);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
