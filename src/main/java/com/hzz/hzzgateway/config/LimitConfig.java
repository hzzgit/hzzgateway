package com.hzz.hzzgateway.config;

import com.hzz.hzzgateway.filter.HostAddrKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：hzz
 * @description：TODO
 * @date ：2021/1/4 14:16
 */
@Configuration
public class LimitConfig {


    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }
}
