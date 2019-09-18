package samet.spring.reactiverest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface BaseEntity {

    String getId();
    void setId(String id);

    @JsonIgnore
    default boolean isIdEmpty(){
        if(getId() == null){
            return true;
        }
        return false;
    }

    // String getName();
    // void setName(String id);
}
