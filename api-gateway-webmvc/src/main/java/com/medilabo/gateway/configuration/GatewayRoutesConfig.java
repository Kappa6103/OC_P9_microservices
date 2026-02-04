package com.medilabo.gateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayRoutesConfig {

    private final static Logger log = LoggerFactory.getLogger(GatewayRoutesConfig.class);

    @Bean
    public RouterFunction<ServerResponse> webFrontendRouteWithAuth() {
        return GatewayRouterFunctions.route("web-frontend")
                .route(path("/patient/**").or(path("/css/**")), HandlerFunctions.http("http://web-frontend:8081"))
                .filter((request, next) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth == null || !auth.isAuthenticated()) {
                        log.error("The request wasn't authenticated");
                        return next.handle(request);
                    }
                    String username = auth.getName();
                    log.info("Forwarding request to web-frontend with X-Auth-Username: {}", username);
                    ServerRequest modifiedRequest = ServerRequest.from(request)
                            .header("X-Auth-Username", username)
                            .build();
                    return next.handle(modifiedRequest);
                })
                .build();
    }
}
