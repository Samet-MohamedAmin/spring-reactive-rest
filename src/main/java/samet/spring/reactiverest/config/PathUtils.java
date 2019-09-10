package samet.spring.reactiverest.config;

import org.springframework.beans.factory.annotation.Value;

public enum PathUtils {
    API_BASE_1("/api/v1"),
    API_BASE_2("/api/v2"),
    BOOKS("/books"),
    AUTHORS("/authors");

    @Value("${server.port}")
    private static String port;

    private final String path;

    PathUtils(String path) {
        this.path = path;
    }
 
    public static String getBaseUrl(){
        return "http://localhost:" + port;
    }
    
    public String getPath() {
        return path;
    }

    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return path;
    }
    
}