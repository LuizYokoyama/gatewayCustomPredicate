package com.ls.gatewayCustomPredicate.predicates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class TokenCaptureRoutePredicateFactory extends
        AbstractRoutePredicateFactory<TokenCaptureRoutePredicateFactory.Config> {



    public TokenCaptureRoutePredicateFactory() {
        super(Config.class);
    }


    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                System.out.println("exchange.getRequest().getHeaders():");
                System.out.println(exchange.getRequest().getHeaders());
                System.out.println("getRequest().getHeaders().get(\"Authorization\"):");
                System.out.println(exchange.getRequest().getHeaders().get("Authorization"));
                String token = exchange.getRequest().getHeaders().get("Authorization").toString();
                token = token.subSequence(8, token.length()-1).toString();
                String[] chunks = token.split("\\.");
                Base64.Decoder decoder = Base64.getUrlDecoder();
                String payload = new String(decoder.decode(chunks[1]));
                Map<String, String> payloadMap;
                try {
                    payloadMap = new ObjectMapper().readValue(payload, HashMap.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Realm: "+payloadMap.get("iss"));
                return true;
            }
        };
    }

    public static class Config {
        public Config( ) {

        }

    }
}