package samet.spring.reactiverest.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.services.BookService;


@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {

        this.service = service;
    }

    @GetMapping
    Flux<Book> list(){

        return service.findAll();
    }

    @GetMapping("/{id}")
    Mono<Book> getById(@PathVariable String id){
        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<Void> create(@RequestBody Flux<Book> entityStream){

        return service.saveAll(entityStream).then();
    }

    @PutMapping("/{id}")
    Mono<Book> update(@PathVariable String id, @RequestBody Book entity) {

        entity.setId(id);
        return service.save(entity);
    }

    @PatchMapping("/{id}")
    Mono<Book> patch(@PathVariable String id, @RequestBody Book entity) {

        return service.updateName(id, entity);
    }

}
