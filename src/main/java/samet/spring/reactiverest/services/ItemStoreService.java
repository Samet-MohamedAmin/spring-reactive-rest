package samet.spring.reactiverest.services;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.models.ItemStore;

public interface ItemStoreService extends EntityService<ItemStore, String> {

    Mono<ItemStore> findByBook(Book book);

    Mono<ItemStore> addQuantity(Book book, Integer quantity);

    Mono<ItemStore> addQuantity(ItemStore itemStore);
}
