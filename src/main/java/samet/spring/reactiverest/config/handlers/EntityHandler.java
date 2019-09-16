package samet.spring.reactiverest.config.handlers;

import java.net.URI;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.config.PathUtils;
import samet.spring.reactiverest.models.BaseEntity;


public class EntityHandler<E extends BaseEntity> implements Handler {


    private ReactiveMongoRepository<E, String> entityRepository;
    private Class<E> theClass;
    private String entityPath;

    public EntityHandler(ReactiveMongoRepository<E, String> entityRepository,
                            Class<E> theClass,
                            String entityPath) {
        this.entityRepository = entityRepository;
        this.theClass = theClass;
        this.entityPath = entityPath;
    }


    public Mono<ServerResponse> list(ServerRequest request) {

        return ServerResponse.ok().body(entityRepository.findAll(), theClass);
    }

    public Mono<ServerResponse> getById(ServerRequest request){

        String id = request.pathVariable("id");

        return ServerResponse.ok().body(entityRepository.findById(id), theClass);
    }

    public Mono<ServerResponse> create(ServerRequest request){

        var authorStream = request.bodyToFlux(theClass);
        var result = entityRepository.saveAll(authorStream).next();
        var authorUri = URI.create(
            PathUtils.getBaseUrl()
            + Handler.getApiBase()
            + entityPath
        );

        return ServerResponse.created(authorUri).body(result, theClass);
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        String id = request.pathVariable("id");
        var entityStream = request.bodyToFlux(theClass)
                                .flatMap(entity -> {
                                    entity.setId(id);
                                    return Mono.just(entity);
                                });
        var result = entityRepository.saveAll(entityStream).next();

        return ServerResponse.accepted().body(result, theClass);
    }

    // public Mono<ServerResponse> patch(ServerRequest request) {

    //     String id = request.pathVariable("id");
    //     var newEntity = request.bodyToMono(theClass);
    //     var foundEntity = entityRepository
    //                         .findById(id)
    //                         .flatMap(entity  -> {
    //                             entity.setName(newEntity.block().getName());
    //                             return Mono.just(entity);
    //                         });

    //     return ServerResponse.accepted().body(entityRepository.saveAll(foundEntity), theClass);
    // }
}
