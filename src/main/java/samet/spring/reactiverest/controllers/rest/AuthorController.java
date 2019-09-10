package samet.spring.reactiverest.controllers.rest;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.services.AuthorService;

/**
 * Created by jt on 12/24/17.
 */
@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    Flux<Author> list(){

        return service.findAll();
    }

    @GetMapping("/{id}")
    Mono<Author> getById(@PathVariable String id){

        return service.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Mono<Void> create(@RequestBody Publisher<Author> entityStream){

        return service.saveAll(entityStream).then();
    }

    @PutMapping("/{id}")
    Mono<Author> update(@PathVariable String id, @RequestBody Author entity) {

        entity.setId(id);
        return service.save(entity);
    }

    @PatchMapping("/{id}")
    Mono<Author> patch(@PathVariable String id, @RequestBody Author entity) {

        return service.updateName(id, entity);
    }

}
