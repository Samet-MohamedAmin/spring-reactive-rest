package samet.spring.reactiverest.services.basic;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.models.ItemStore;
import samet.spring.reactiverest.repositories.ItemStoreRepository;
import samet.spring.reactiverest.services.BookService;
import samet.spring.reactiverest.services.EntityService;
import samet.spring.reactiverest.services.ItemStoreService;

@Service
public class ItemStoreServiceImpl implements ItemStoreService {

    private ItemStoreRepository repository;
    private BookService bookService;
    private EntityService<ItemStore, String> service;

    ItemStoreServiceImpl(
            ItemStoreRepository repository,
            BookService bookService ) {

        this.repository = repository;
        this.bookService = bookService;
        service = new EntityServiceImpl<>(this.repository);
    }

    @Override
    public Flux<ItemStore> findAll() {

        return service.findAll();
    }

    @Override
    public Mono<ItemStore> findById(String id) {

        return service.findById(id);
    }

    @Override
    public Mono<ItemStore> save(ItemStore entity) {

        Book book = entity.getBook();
        Integer quantity = entity.getQuantity();
        if(book.isIdEmpty()) {
            return bookService.save(book).flatMap(theBook -> {
                var itemStore = ItemStore.builder().book(theBook).quantity(0).build();
                itemStore.addQuantity(quantity);
                return service.save(itemStore);
            });
        }

        return findByBook(book)
                .defaultIfEmpty(ItemStore.builder().book(book).quantity(0).build())
                .flatMap(itemStore -> {
                    itemStore.addQuantity(quantity);
                    return service.save(itemStore);
                });
    }

    @Override
    public Mono<ItemStore> addQuantity(Book book, Integer quantity) {

        ItemStore itemStore = ItemStore.builder().book(book).quantity(quantity).build();
        return save(itemStore);
    }

    @Override
    public Mono<ItemStore> addQuantity(ItemStore itemStore) {

        return save(itemStore);
    }

    @Override
    public Flux<ItemStore> saveAll(Flux<ItemStore> entityStream) {

        return entityStream.flatMap(this::save);
    }

    @Override
    public Mono<Long> count() {

        return service.count();
    }

    @Override
    public Mono<ItemStore> findByBook(Book book) {

        return repository.findByBook(book);
    }
}

