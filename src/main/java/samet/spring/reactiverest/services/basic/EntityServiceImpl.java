package samet.spring.reactiverest.services.basic;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.BaseEntity;
import samet.spring.reactiverest.services.EntityService;

public class EntityServiceImpl<E extends BaseEntity, ID> implements EntityService<E, ID> {

    ReactiveMongoRepository<E, ID> repository;

    EntityServiceImpl(ReactiveMongoRepository<E, ID> repository) {

        this.repository = repository;
    }

    @Override
    public Flux<E> findAll() {

        return repository.findAll();
    }

    @Override
    public Mono<E> findById(ID id) {

        return repository.findById(id);
    }

    @Override
    public Mono<E> save(E entity) {

        return repository.save(entity);
    }

    @Override
    public Flux<E> saveAll(Flux<E> entityStream) {

        return repository.saveAll(entityStream);
    }

    @Override
    public Mono<Long> count() {

        return repository.count();
    }

}


