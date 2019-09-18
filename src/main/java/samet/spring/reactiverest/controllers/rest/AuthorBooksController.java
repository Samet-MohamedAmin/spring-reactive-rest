package samet.spring.reactiverest.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.AuthorBooks;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.services.AuthorBooksService;
import samet.spring.reactiverest.services.AuthorService;

/**
 * Created by jt on 12/24/17.
 */
@RestController
@RequestMapping("/api/v1/authorbooks")
public class AuthorBooksController {

    private AuthorBooksService service;
    private AuthorService authorService;

    @Autowired
    public AuthorBooksController(
            AuthorBooksService service,
            AuthorService authorService) {
        this.service = service;
        this.authorService = authorService;
    }

    @GetMapping
    Flux<AuthorBooks> list(){

        return service.findAll();
    }

    @GetMapping("/{id}")
    Mono<AuthorBooks> getById(@PathVariable String id){

        return service.findById(id);
    }

    @GetMapping("/author/{id}")
    public Mono<AuthorBooks> getByAuthorId(@PathVariable String id){

        return authorService.findById(id).flatMap(author -> service.findByAuthor(author));
    }

    @PutMapping("/{id}")
    Mono<AuthorBooks> update(@PathVariable String id, @RequestBody AuthorBooks entity) {

        entity.setId(id);
        return service.save(entity);
    }


    @PatchMapping("/{id}")
    Mono<AuthorBooks> patchBook(
            @PathVariable String id,
            @RequestBody Book book) {

        return service.findById(id).flatMap(authorBooks -> service.addBook(authorBooks, book));
    }

    @PatchMapping("/author/{id}")
    Mono<AuthorBooks> patchBookAuthorId(
            @PathVariable String id,
            @RequestBody Book book) {

        return authorService.findById(id).flatMap(author -> service.addBook(author, book));
    }
}

