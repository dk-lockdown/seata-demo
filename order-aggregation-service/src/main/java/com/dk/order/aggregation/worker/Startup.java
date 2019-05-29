package com.dk.order.aggregation.worker;

import com.dk.foundation.annotation.EnableEngineStart;
import com.dk.foundation.engine.filter.SeataXidFilter;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableEngineStart
@EnableFeignClients
@EnableCaching
public class Startup {
    public static void main(String[] args) {
        SpringApplication.run(Startup.class,args);
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SeataXidFilter seataXidFilter(){
        return new SeataXidFilter();
    }

    @Bean
    public GlobalTransactionScanner scanner(){
        GlobalTransactionScanner scanner = new GlobalTransactionScanner("order_aggregation_service","order_aggregation_service_group");
        return scanner;
    }
}
