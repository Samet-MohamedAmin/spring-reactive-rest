package samet.spring.reactiverest.config.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.config.PathUtils;

public interface Handler {

    public Mono<ServerResponse> list(ServerRequest request);
    public Mono<ServerResponse> getById(ServerRequest request);
    public Mono<ServerResponse> create(ServerRequest request);
    public Mono<ServerResponse> update(ServerRequest request);
    public Mono<ServerResponse> patch(ServerRequest request);

    public static String getApiBase() {

        return PathUtils.API_BASE_2.getPath();
    }
}
