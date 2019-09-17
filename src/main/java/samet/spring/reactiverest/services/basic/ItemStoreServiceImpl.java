package samet.spring.reactiverest.services.basic;

import java.util.ArrayList;

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

        return addBooks(entity.getBook(), entity.getQuantity());
    }

    @Override
    public Flux<ItemStore> saveAll(Flux<ItemStore> entityStream) {

        var itemsList = new ArrayList<Mono<ItemStore>>();
        var items = entityStream.collectList().block();
        for(var item: items) {
            itemsList.add(addBooks(item.getBook(), item.getQuantity()));
        }
        return Flux.concat(itemsList);
    }

    @Override
    public Mono<Long> count() {

        return service.count();
    }

    @Override
    public Mono<ItemStore> addBooks(Book book, Integer quantity) {

        if(book.isIdEmpty()) {
            bookService.save(book).block();
        }
        ItemStore store = findByBook(book).block();
        if(store == null) {
            store = ItemStore.builder().book(book).quantity(0).build();
        }

        store.addQuantity(quantity);
        return service.save(store);
    }

    @Override
    public Mono<ItemStore> findByBook(Book book) {

        return repository.findByBook(book);
    }
}

