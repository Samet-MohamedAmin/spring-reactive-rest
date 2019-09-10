package samet.spring.reactiverest.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
public class BaseController {

    private final static String API_BASE_URL = "/api/v1";
    private final static String API_BASE_URL_2 = "/api/v2";

    @GetMapping("/test")
    public String testController() {

        return "test is fine";
    }


    @GetMapping("/")
    public Mono<Map<?, ?>> indexController() {

        return apiController();
    }

    @GetMapping(API_BASE_URL)
    public Mono<Map<?, ?>> apiController() {

        var apiLinksMap = new HashMap<String, String>();
        String baseUrl = "http://localhost:8080" + API_BASE_URL_2;

        apiLinksMap.put("books description", baseUrl + "/books");
        apiLinksMap.put("authors description", baseUrl + "/authors");
        return Mono.just(apiLinksMap);
    }

    // @ExceptionHandler({ Exception.class })
    // public String  handleException(HttpServerRequest req, Exception ex) {
        
    //     System.err.println(ex);
    //     return ex.getMessage();
    // }

}