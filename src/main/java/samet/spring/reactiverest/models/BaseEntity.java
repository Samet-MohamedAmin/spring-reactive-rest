package samet.spring.reactiverest.models;


public interface BaseEntity {

    String getId();
    void setId(String id);

    default boolean isIdEmpty(){
        if(getId() == null){
            return true;
        }
        return false;
    }

    // String getName();
    // void setName(String id);
}
