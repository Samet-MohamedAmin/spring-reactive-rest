package samet.spring.reactiverest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.models.ItemStore;


public interface ItemStoreRepository extends ReactiveMongoRepository<ItemStore, String> {

    Mono<ItemStore> findByBook(Book book);
}
