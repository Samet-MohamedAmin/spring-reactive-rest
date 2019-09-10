package samet.spring.reactiverest.services.basic;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.repositories.BookRepository;
import samet.spring.reactiverest.services.BookService;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.services.EntityService;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;
    private EntityService<Book, String> service;

    BookServiceImpl(BookRepository repository) {

        this.repository = repository;
        service = new EntityServiceImpl<>(this.repository);
    }

    @Override
    public Flux<Book> findAll() {

        return service.findAll();
    }

    @Override
    public Mono<Book> findById(String id) {

        return service.findById(id);
    }

    @Override
    public Mono<Book> save(Book entity) {

        return service.save(entity);
    }

    @Override
    public Flux<Book> saveAll(Publisher<Book> entityStream) {

        return service.saveAll(entityStream);
    }

    @Override
    public Mono<Book> updateName(String id, Book entity) {

        return service.updateName(id, entity);
    }
}

