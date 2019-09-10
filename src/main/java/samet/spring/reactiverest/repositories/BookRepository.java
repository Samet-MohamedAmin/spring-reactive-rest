package samet.spring.reactiverest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import samet.spring.reactiverest.models.Book;

/**
 * Created by jt on 12/23/17.
 */
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
