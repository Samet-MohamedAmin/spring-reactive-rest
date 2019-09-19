package samet.spring.reactiverest.config.handlers;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import samet.spring.reactiverest.config.PathUtils;
import samet.spring.reactiverest.models.ItemStore;
import samet.spring.reactiverest.services.ItemStoreService;


@Service
public class ItemStoreHandler implements Handler {


    private final ItemStoreService service;
    private Handler handler;

    public ItemStoreHandler(ItemStoreService service) {
        this.service = service;

        this.handler = new EntityHandler<ItemStore>(this.service,
                                ItemStore.class,
                                getEntityPath());
    }

    public static String getEntityPath() {

        return PathUtils.ITEMSTORE.getPath();
    }

    public Mono<ServerResponse> list(ServerRequest request) {

        return handler.list(request);
    }

    public Mono<ServerResponse> getById(ServerRequest request){

        return handler.getById(request);
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        return handler.update(request);
    }

    // public Mono<ServerResponse> patch(ServerRequest request) {

    //     return handler.patch(request);
    // }
}
