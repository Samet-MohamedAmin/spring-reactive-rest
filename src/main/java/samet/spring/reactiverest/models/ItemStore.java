package samet.spring.reactiverest.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemStore implements BaseEntity {

    private static final String QUANTITY_ERROR_MSG = "EXISTING BOOK QUANTITY IS LESS THAN QUANTITY TO REMOVE";

    @Id
    private String id;
    
    @DBRef
    private Book book;

    private Integer quantity;

    public void addQuantity(Integer quantity) {

        this.quantity += quantity;
    }

    public void removeItem(Integer quantity) throws RuntimeException {

        Integer theQuantity = this.quantity - quantity;
        if(theQuantity < 0){
            throw new RuntimeException(QUANTITY_ERROR_MSG);
        }

        this.quantity = theQuantity;
    }

}

