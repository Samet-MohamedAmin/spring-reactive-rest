package samet.spring.reactiverest.models;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBooks {

    @Id
    private String id;

    @DBRef
    private Author author;

    @DBRef
    @Builder.Default
    private Set<Book> books = new HashSet<>();


    public void addBook(Book book) {

        books.add(book);
    }

    public void addBook(Mono<Book> book) {

        addBook(book.block());
    }

    public void addBookList(Collection<Book> bookList) {

        books.addAll(bookList);
    }

    public void addBookList(Flux<Book> bookList) {

        addBookList(bookList.collectList().block());
    }
}
