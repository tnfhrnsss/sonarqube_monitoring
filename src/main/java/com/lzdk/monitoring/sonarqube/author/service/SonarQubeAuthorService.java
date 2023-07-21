package com.lzdk.monitoring.sonarqube.author.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lzdk.monitoring.sonarqube.client.model.Issue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SonarQubeAuthorService {
    private static final Map<String, ConcurrentHashMap.KeySetView> targets = new ConcurrentHashMap<>();

    public void enroll(Issue issue) {
        targets.computeIfAbsent(issue.getAuthor(), k -> ConcurrentHashMap.newKeySet()).add(issue.getProject());
    }

    public Map findAll() {
        return targets;
        /*System.out.println("===============");
        System.out.println(targets.size());
        targets.forEach((a, b) -> {
            System.out.println(a.toString() + "---"+ b.toString());
        });*/
    }
}
