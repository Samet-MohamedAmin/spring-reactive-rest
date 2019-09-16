package samet.spring.reactiverest.services;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.AuthorBooks;
import samet.spring.reactiverest.models.Book;

public interface AuthorBooksService extends EntityService<AuthorBooks, String> {

    Mono<AuthorBooks> findByAuthor(Author author);

    Mono<AuthorBooks> addAuthor(Author author, Book book);
}
