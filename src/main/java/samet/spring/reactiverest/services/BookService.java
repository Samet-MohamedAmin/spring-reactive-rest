package samet.spring.reactiverest.services;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.models.Author;
import samet.spring.reactiverest.models.Book;

public interface BookService extends EntityService<Book, String> {

    Mono<Book> updateName(String id, Book entity);
    
    Mono<Book> addAuthor(Book book, Author author);
}
