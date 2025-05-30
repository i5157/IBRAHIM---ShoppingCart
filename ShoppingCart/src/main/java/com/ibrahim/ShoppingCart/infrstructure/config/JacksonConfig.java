package com.ibrahim.ShoppingCart.infrstructure.config;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Hibernate5JakartaModule hibernate5Module() {
        Hibernate5JakartaModule module = new Hibernate5JakartaModule();
        module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, false);
        module.configure(Hibernate5JakartaModule.Feature.USE_TRANSIENT_ANNOTATION, false);
        return module;
    }
}