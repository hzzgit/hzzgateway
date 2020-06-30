package com.hzz.hzzgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component("autoCutPathFilter")
public class AutoCutPathFilter implements GatewayFilter {
    public static final String Base_Prefix = "/webgw";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest newrequest = exchange.getRequest();
        String path = newrequest.getURI().getRawPath();
        if (path.startsWith(AutoCutPathFilter.Base_Prefix)) {
            String newPath = path.replaceFirst(AutoCutPathFilter.Base_Prefix, "");
            newrequest = newrequest.mutate().path(newPath).build();
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newrequest.getURI());
        }
        if (path.startsWith("/debugwebgw")) {
            String newPath = path.replaceFirst("/debugwebgw", "");
            newrequest = newrequest.mutate().path(newPath).build();
            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, newrequest.getURI());
        }
        newrequest.mutate().headers(httpHeaders -> {
            httpHeaders.add("userId", String.valueOf(1));
            httpHeaders.add("userName", String.valueOf("avcc"));
        }).build();
        return chain.filter(exchange.mutate().request(newrequest).build());
    }
}
