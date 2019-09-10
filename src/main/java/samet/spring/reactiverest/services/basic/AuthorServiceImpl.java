package samet.spring.reactiverest.services.basic;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.repositories.AuthorRepository;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.services.AuthorService;
import samet.spring.reactiverest.services.EntityService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository repository;
    private EntityService<Author, String> service;

    AuthorServiceImpl(AuthorRepository repository) {

        this.repository = repository;
        service = new EntityServiceImpl<>(this.repository);
    }

    @Override
    public Flux<Author> findAll() {

        return service.findAll();
    }

    @Override
    public Mono<Author> findById(String id) {

        return service.findById(id);
    }

    @Override
    public Mono<Author> save(Author entity) {

        return service.save(entity);
    }

    @Override
    public Flux<Author> saveAll(Publisher<Author> entityStream) {

        return service.saveAll(entityStream);
    }

    @Override
    public Mono<Author> updateName(String id, Author entity) {

        return service.updateName(id, entity);
    }
}

