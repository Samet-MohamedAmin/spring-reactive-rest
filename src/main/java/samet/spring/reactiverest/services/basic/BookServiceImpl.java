package samet.spring.reactiverest.services.basic;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.repositories.BookRepository;
import samet.spring.reactiverest.services.AuthorBooksService;
import samet.spring.reactiverest.services.BookService;
import samet.spring.reactiverest.services.EntityService;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;
    private AuthorBooksService authorBooksService;
    private EntityService<Book, String> service;

    BookServiceImpl(
            BookRepository repository,
            AuthorBooksService authorBooksService) {

        this.repository = repository;
        this.authorBooksService = authorBooksService;
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
    public Flux<Book> saveAll(Flux<Book> entityStream) {

        return service.saveAll(entityStream);
    }

    @Override
    public Mono<Book> updateName(String id, Book entity) {

        // Author foundAuthor = authorService.findById(id).block();

        // if(foundAuthor.getName() != author.getName()){
        //     foundAuthor.setName(author.getName());
        //     return authorService.save(foundAuthor);
        // }

        return null;
    }

    @Override
    public Mono<Long> count() {

        return service.count();
    }

    /**
     * 
     * Adds author to book item and saves eventually the book item.
     */
    @Override
    public Mono<Book> addAuthor(Book book, Author author) {
        
        if(book.isIdEmpty()){
            save(book).block();
        }
        book.addAuthor(author);
        save(book).block();
        authorBooksService.addAuthor(author, book).block();
        return Mono.just(book);
    }
}

