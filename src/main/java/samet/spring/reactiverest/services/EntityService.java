package samet.spring.reactiverest.services;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.BaseEntity;

public interface EntityService<E extends BaseEntity, ID> {

    Flux<E> findAll();

    Mono<E> findById(ID id);

    Mono<E> save(E entity);
    
    Flux<E> saveAll(Flux<E> entityStream);

    Mono<E> updateName(ID id, E entity);

    Mono<Long> count();

}
