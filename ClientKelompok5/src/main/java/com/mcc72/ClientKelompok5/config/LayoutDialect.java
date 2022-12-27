package com.mcc72.ClientKelompok5.config;

import org.springframework.context.annotation.Bean;

public class LayoutDialect {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
