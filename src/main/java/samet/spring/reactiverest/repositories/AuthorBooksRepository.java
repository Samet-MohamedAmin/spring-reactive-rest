package samet.spring.reactiverest.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.AuthorBooks;

/**
 * Created by jt on 12/23/17.
 */
public interface AuthorBooksRepository extends ReactiveMongoRepository<AuthorBooks, String> {

    Mono<AuthorBooks> findByAuthor(Author author);
}
