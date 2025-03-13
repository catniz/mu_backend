package com.musinsa.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
public class ApiPrefixMapping extends RequestMappingHandlerMapping {

    @Value("${api.prefix:/api}")
    private String apiPrefix;

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        // add "/api" prefix for restController
        if (isApiController(handler)) {
            mapping = RequestMappingInfo.paths(apiPrefix).build().combine(mapping);
        }

        super.registerHandlerMethod(handler, method, mapping);
    }

    private boolean isApiController(Object handler) {
        if (handler instanceof String) {
            handler = Optional.ofNullable(getApplicationContext()).
                    orElseThrow(() -> new IllegalStateException("Handler must not be null")).
                    getBean((String) handler);
        }
        return AnnotatedElementUtils.hasAnnotation(handler.getClass(), RestController.class);
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        getHandlerMethods().forEach((key, value) ->
                System.out.println("Mapped URL: " + key + " -> " + value)
        );
    }
}
