package samet.spring.reactiverest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Book;
import samet.spring.reactiverest.models.BookAuthors;

/**
 * Created by jt on 12/23/17.
 */
public interface BookAuthorsRepository extends ReactiveMongoRepository<BookAuthors, String> {

    Mono<BookAuthors> findByBook(Book book);
}
