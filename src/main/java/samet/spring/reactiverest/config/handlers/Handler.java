package samet.spring.reactiverest.config.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.config.PathUtils;

public interface Handler {

    public Mono<ServerResponse> list(ServerRequest request);
    public Mono<ServerResponse> getById(ServerRequest request);
    default public Mono<ServerResponse> create(ServerRequest request) throws RuntimeException {

        throw new RuntimeException("THIS METHOD SHOULD NOT BE CALLED");
    };
    public Mono<ServerResponse> update(ServerRequest request);
    // public Mono<ServerResponse> patch(ServerRequest request);

    public static String getApiBase() {

        return PathUtils.API_BASE_2.getPath();
    }
}
