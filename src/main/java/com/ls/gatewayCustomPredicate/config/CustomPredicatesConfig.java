package com.ls.gatewayCustomPredicate.config;

import com.ls.gatewayCustomPredicate.predicates.TokenCaptureRoutePredicateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomPredicatesConfig {
    @Bean
    public TokenCaptureRoutePredicateFactory asdfdf() {
        return new TokenCaptureRoutePredicateFactory();
    }

}