package com.lzdk.monitoring.sonarqube.controller;

import com.lzdk.monitoring.sonarqube.service.SonarQubeMonitorFlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("sonarqube")
public class SonarQubeMonitorController {
    private final SonarQubeMonitorFlowService sonarQubeMonitorFlowService;

    @PostMapping("codesmell/alert")
    public Mono<Void> alert() {
        return Mono.fromRunnable(() -> sonarQubeMonitorFlowService.alert());
    }
}
