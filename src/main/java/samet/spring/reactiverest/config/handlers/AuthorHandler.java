package samet.spring.reactiverest.config.handlers;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.config.PathUtils;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.repositories.AuthorRepository;


@Service
public class AuthorHandler implements Handler {


    private final AuthorRepository authorRepository;
    private Handler handler;

    public AuthorHandler(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;

        this.handler = new EntityHandler<Author>(this.authorRepository,
                                Author.class,
                                getEntityPath());
    }

    public static String getEntityPath() {

        return PathUtils.AUTHORS.getPath();
    }

    public Mono<ServerResponse> list(ServerRequest request) {

        return handler.list(request);
    }

    public Mono<ServerResponse> getById(ServerRequest request){

        return handler.getById(request);
    }

    public Mono<ServerResponse> create(ServerRequest request){
        
        return handler.create(request);
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        return handler.update(request);
    }

    // public Mono<ServerResponse> patch(ServerRequest request) {

    //     return handler.patch(request);
    // }
}
