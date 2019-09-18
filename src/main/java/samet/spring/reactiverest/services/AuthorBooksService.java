package samet.spring.reactiverest.services;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.AuthorBooks;
import samet.spring.reactiverest.models.Book;

public interface AuthorBooksService extends EntityService<AuthorBooks, String> {

    Mono<AuthorBooks> findByAuthor(Author author);

    Mono<AuthorBooks> addBook(Author author, Book book);

    Mono<AuthorBooks> addBook(AuthorBooks authorBooks, Book book);
}
