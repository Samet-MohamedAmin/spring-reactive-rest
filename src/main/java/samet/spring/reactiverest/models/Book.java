package samet.spring.reactiverest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements BaseEntity {

    @Id
    private String id;

    private String name;

    // @DBRef
    // @Builder.Default
    // private Set<Author> authors = new HashSet<>();

    // public void addAuthor(Mono<Author> author) {

    //     Author theAuthor = author.block();
    //     theAuthor.addBook(Mono.just(this));
    //     authors.add(author.block());
    // }

    // public void addBookList(Flux<Author> authorList) {

    //     authors.addAll(authorList.collectList().block());
    // }

}

