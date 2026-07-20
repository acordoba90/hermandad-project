package com.hermandadproject.apigateway;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/** Pruebas de contexto, registro de rutas y política CORS del Gateway. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiGatewayApplicationTests {

    private static final String ALLOWED_ORIGIN = "http://localhost:5173";
    private static final DisposableServer CORS_UPSTREAM = HttpServer.create()
            .host("127.0.0.1")
            .port(0)
            .handle((request, response) -> response
                    .header("Access-Control-Allow-Origin", ALLOWED_ORIGIN)
                    .sendString(reactor.core.publisher.Mono.just("{}")))
            .bindNow();

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private WebTestClient webTestClient;

    @DynamicPropertySource
    static void configureCorsUpstream(DynamicPropertyRegistry registry) {
        registry.add("cors.upstream.url",
                () -> "http://127.0.0.1:" + CORS_UPSTREAM.port());
    }

    @AfterAll
    static void stopCorsUpstream() {
        CORS_UPSTREAM.disposeNow();
    }

    @Test
    void contextLoadsAndRegistersExpectedRoutes() {
        Set<String> routeIds = routeDefinitionLocator.getRouteDefinitions()
                .map(definition -> definition.getId()).collectList().block().stream().collect(Collectors.toSet());
        assertThat(routeIds).contains("gestion-usuarios", "gestion-personajes", "gestion-hermandades");
    }

    @Test
    void configuredOriginCanCompletePreflight() {
        webTestClient.options().uri("/api/users")
                .header("Origin", ALLOWED_ORIGIN)
                .header("Access-Control-Request-Method", "POST")
                .exchange()
                .expectHeader().valueEquals("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
    }

    @Test
    void downstreamCorsHeaderIsNotDuplicated() {
        webTestClient.get().uri("/test/cors")
                .header("Origin", ALLOWED_ORIGIN)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().values("Access-Control-Allow-Origin",
                        values -> assertThat(values).containsExactly(ALLOWED_ORIGIN));
    }

    @Test
    void unknownOriginIsNotAllowedByCors() {
        webTestClient.options().uri("/api/users")
                .header("Origin", "https://example.invalid")
                .header("Access-Control-Request-Method", "POST")
                .exchange()
                .expectHeader().doesNotExist("Access-Control-Allow-Origin");
    }

}
