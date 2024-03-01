package handlers;

import com.google.gson.Gson;
import spark.*;

public class Handler {
    protected static Gson serializer = new Gson();
    protected static <T> T getBody(Request request, Class<T> clazz) {
        // parses the request body into an object of any class
        var body = serializer.fromJson(request.body(), clazz);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }
}
