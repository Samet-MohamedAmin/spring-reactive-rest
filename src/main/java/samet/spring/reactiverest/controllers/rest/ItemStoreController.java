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
import samet.spring.reactiverest.models.ItemStore;
import samet.spring.reactiverest.services.BookService;
import samet.spring.reactiverest.services.ItemStoreService;

/**
 * Created by jt on 12/24/17.
 */
@RestController
@RequestMapping("/api/v1/itemstore")
public class ItemStoreController {

    private ItemStoreService service;
    private BookService bookService;

    @Autowired
    public ItemStoreController(
            ItemStoreService service,
            BookService bookService) {
        this.service = service;
        this.bookService = bookService;
    }

    @GetMapping
    Flux<ItemStore> list(){

        return service.findAll();
    }

    @GetMapping("/{id}")
    Mono<ItemStore> getById(@PathVariable String id){

        return service.findById(id);
    }

    @GetMapping("/book/{id}")
    public Mono<ItemStore> getByBookId(@PathVariable String id){

        return bookService.findById(id).flatMap(book -> service.findByBook(book));
    }

    @PutMapping("/{id}")
    Mono<ItemStore> update(@PathVariable String id, @RequestBody ItemStore entity) {

        entity.setId(id);
        return service.save(entity);
    }


    /**
     * 
     * @param id
     * @param entity
     * @return
     * NOTE: entity from request body may not include book so I have to search for the book value.
     */
    @PatchMapping("/{id}")
    Mono<ItemStore> patchQuantity(
            @PathVariable String id,
            @RequestBody ItemStore entity) {

        return service.findById(id).flatMap(itemStore -> service.addQuantity(itemStore.getBook(), entity.getQuantity()));
    }

    /**
     * 
     * @param id
     * @param item
     * @return
     * NOTE: entity from request body may not include book so I have to search for the book value.
     */
    @PatchMapping("/book/{id}")
    Mono<ItemStore> patchBookAuthorId(
            @PathVariable String id,
            @RequestBody ItemStore entity) {

        return bookService.findById(id).flatMap(book -> service.addQuantity(book, entity.getQuantity()));
    }
}

