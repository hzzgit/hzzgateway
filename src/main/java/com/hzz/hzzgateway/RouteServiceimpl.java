package com.hzz.hzzgateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteServiceimpl implements RouteLocator {
    Flux<Route> routeFlux = Flux.empty();

    @Autowired
    private ConfigurableApplicationContext applicationContext;

    private RouteLocatorBuilder builder;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Override
    public Flux<Route> getRoutes() {
        return routeFlux;
    }

    public RouteServiceimpl(RouteLocatorBuilder builder) {
        this.builder = builder;
    }


    @PostConstruct
    private void init() {
        new Thread(() -> {
            try {

                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<GatewayFilter> filters = new ArrayList<>();
            filters.add((GatewayFilter) applicationContext.getBean("autoCutPathFilter"));
            filters.add((GatewayFilter) applicationContext.getBean("onlineUserFilter"));
            //filters.add((GatewayFilter) applicationContext.getBean("gatewayLogFilter"));
            RouteLocator build = builder.routes()
                    .route(r -> r.path("/test/**").filters(f -> f.filters(filters))
                            .uri("lb://cloudce/"))
                    .route(r -> r.path("/test/**").filters(f -> f.filters(filters))
                            .uri("lb://cloudce/"))
                    .build();
            routeFlux = build.getRoutes();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));

        }).start();
    }
}
