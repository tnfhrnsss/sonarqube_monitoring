package com.lzdk.monitoring.sonarqube.controller;

import com.lzdk.monitoring.sonarqube.service.SonarQubeMonitorFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("sonarqube")
public class SonarQubeMonitorController {
    private final SonarQubeMonitorFlowService sonarQubeMonitorFlowService;

    @PostMapping("codesmells/alert")
    public void alert() {
        sonarQubeMonitorFlowService.alert();
    }
}
