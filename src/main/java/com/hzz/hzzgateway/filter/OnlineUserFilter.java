package com.hzz.hzzgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2020/11/24 17:04
 */
@Service("onlineUserFilter")
public class OnlineUserFilter implements GatewayFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("token");
        if (token.equalsIgnoreCase("1")) {
            ServerHttpRequest request1 = exchange.getRequest().mutate().headers(hd -> {
                hd.add("ssoSign", "1");
                hd.add("ssoSessionId", "2");
                hd.add("ssoUserId", "3");
            }).build();
            ServerWebExchange build = exchange.mutate().request(request1).build();
           return chain.filter(build);
        }else{
            throw new RuntimeException("session timeout");
        }
    }
}
