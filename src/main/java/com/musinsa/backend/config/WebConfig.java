package com.musinsa.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@Configuration
public class WebConfig extends DelegatingWebMvcConfiguration {

    @Override
    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new ApiPrefixMapping();
    }

    @Bean
    @Primary
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        return super.requestMappingHandlerMapping(contentNegotiationManager, conversionService, resourceUrlProvider);
    }
}
