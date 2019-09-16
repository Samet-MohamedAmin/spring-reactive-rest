package samet.spring.reactiverest.services;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;

public interface AuthorService extends EntityService<Author, String> {

    Mono<Author> updateName(String id, Author entity);
}
