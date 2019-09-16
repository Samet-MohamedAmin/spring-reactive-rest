package samet.spring.reactiverest.models;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store{

    private static final String QUANTITY_ERROR_MSG = "EXISTING BOOK QUANTITY IS LESS THAN QUANTITY TO REMOVE";

    @Id
    private String id;

    @Builder.Default
    private Map<String, Integer> items = new HashMap<>();

    public void addItem(Book book, Integer quantity) {

        items.put(book.getId(), quantity);
    }

    public void addItem(Mono<Book> book, Integer quantity) {

        addItem(book.block(), quantity);
    }

    public void removeItem(Book book, Integer quantity) throws RuntimeException {

        String id = book.getId();
        Integer theQuantity = items.get(id) - quantity;
        if(theQuantity < 0){
            throw new RuntimeException(QUANTITY_ERROR_MSG);
        }
        items.put(book.getId(), theQuantity);
    }

    public void removeItem(Mono<Book> book, Integer quantity) throws RuntimeException {

        addItem(book.block(), quantity);
    }

}

