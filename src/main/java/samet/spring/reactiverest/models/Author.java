package samet.spring.reactiverest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author implements BaseEntity {

    @Id
    private String id;

    private String name;

    @DBRef
    @Builder.Default
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {

        books.add(book);
    }

    public void addBook(Mono<Book> book) {

        books.add(book.block());
    }

    public void addBookList(Collection<Book> bookList) {

        books.addAll(bookList);
    }

    public void addBookList(Flux<Book> bookList) {

        books.addAll(bookList.collectList().block());
    }
}

