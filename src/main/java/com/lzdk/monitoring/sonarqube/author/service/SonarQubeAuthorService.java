package com.lzdk.monitoring.sonarqube.author.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.sonarqube.author.sdo.AuthorSdo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarQubeAuthorService {
    private static final Map<String, ConcurrentHashMap.KeySetView> targets = new ConcurrentHashMap<>();

    public void enroll(AuthorSdo authorSdo) {
        targets.computeIfAbsent(authorSdo.getAuthor(), k -> ConcurrentHashMap.newKeySet()).add(authorSdo.getProject());
    }

    public Map findAll() {
        return targets;
    }

    public void remove() {
        targets.clear();
    }
}
