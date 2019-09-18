package samet.spring.reactiverest.services.basic;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.AuthorBooks;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.repositories.AuthorBooksRepository;
import samet.spring.reactiverest.services.AuthorBooksService;
import samet.spring.reactiverest.services.EntityService;

@Service
public class AuthorBooksServiceImpl implements AuthorBooksService {

    private AuthorBooksRepository repository;
    private EntityService<AuthorBooks, String> service;

    AuthorBooksServiceImpl(AuthorBooksRepository repository) {

        this.repository = repository;
        service = new EntityServiceImpl<>(this.repository);
    }

    @Override
    public Flux<AuthorBooks> findAll() {

        return service.findAll();
    }

    @Override
    public Mono<AuthorBooks> findById(String id) {

        return service.findById(id);
    }

    @Override
    public Mono<AuthorBooks> save(AuthorBooks entity) {

        return service.save(entity);
    }

    @Override
    public Flux<AuthorBooks> saveAll(Flux<AuthorBooks> entityStream) {

        return service.saveAll(entityStream);
    }

    @Override
    public Mono<Long> count() {

        return service.count();
    }

    @Override
    public Mono<AuthorBooks> addBook(Author author, Book book) {

        return findByAuthor(author).flatMap(authorBooks -> {
            authorBooks.addBook(book);
            return save(authorBooks);
        });
    }


    @Override
    public Mono<AuthorBooks> addBook(AuthorBooks authorBooks, Book book) {

        authorBooks.addBook(book);
        return save(authorBooks);
    }

    @Override
    public Mono<AuthorBooks> findByAuthor(Author author) {

        return repository.findByAuthor(author);
    }
}

