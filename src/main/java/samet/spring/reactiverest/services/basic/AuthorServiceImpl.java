package samet.spring.reactiverest.services.basic;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.AuthorBooks;
import samet.spring.reactiverest.repositories.AuthorRepository;
import samet.spring.reactiverest.services.AuthorBooksService;
import samet.spring.reactiverest.services.AuthorService;
import samet.spring.reactiverest.services.EntityService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository repository;
    private EntityService<Author, String> service;
    private AuthorBooksService authorBooksService;

    AuthorServiceImpl(AuthorRepository repository, AuthorBooksService authorBooksService){

        this.repository = repository;
        this.authorBooksService = authorBooksService;
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

        var authorMono = service.save(entity);
        if(entity.isIdEmpty()) {

            return authorMono.flatMap(author -> {
                return authorBooksService.save(AuthorBooks.builder().author(author).build()).flatMap(authorBooks -> {
                    return Mono.just(authorBooks.getAuthor());
                });
            });
        }

        return authorMono;
    }

    @Override
    public Flux<Author> saveAll(Flux<Author> entityStream) {

        var authorStream = entityStream.flatMap(entity -> {
            var authorMono = service.save(entity);
            
            if(entity.isIdEmpty()) {
                return authorMono.flatMap(author -> {
                    return authorBooksService.save(AuthorBooks.builder().author(author).build()).flatMap(authorBooks -> {
                        return Mono.just(authorBooks.getAuthor());
                    });
                });
            }
            return authorMono;
        });
        return authorStream;
    }

    @Override
    public Mono<Author> updateName(String id, Author entity) {

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
}

