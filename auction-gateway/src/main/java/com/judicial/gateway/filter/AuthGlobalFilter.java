package com.judicial.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * B 端页面（/b-*.html）越权保护：无 session cookie 则重定向 /login.html
 * 说明：微服务简化演示，实际生产用 JWT 无状态校验。
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        if (path.startsWith("/b-")) {
            boolean hasAuth = exchange.getRequest().getCookies().containsKey("ROLE");
            if (!hasAuth) {
                exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.FOUND);
                exchange.getResponse().getHeaders().set("Location", "/login.html");
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }

    @Override public int getOrder() { return -100; }
}
