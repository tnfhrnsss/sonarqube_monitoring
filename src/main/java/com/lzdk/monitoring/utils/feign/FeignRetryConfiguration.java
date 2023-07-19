package com.lzdk.monitoring.utils.feign;

import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignRetryConfiguration {
    @Bean
    public Retryer retryer(@Value("${messengerconnector.feign.retryPeriod:0}") final int retryPeriod,
                           @Value("${messengerconnector.feign.retryMaxPeriod:0}") final int retryMaxPeriod,
                           @Value("${messengerconnector.feign.retryMaxAttempts:0}") final int retryMaxAttempts) {
        return new Retryer.Default(retryPeriod, retryMaxPeriod, retryMaxAttempts);
    }
}
