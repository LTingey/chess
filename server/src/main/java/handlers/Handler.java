package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import spark.*;

import java.util.Map;

public class Handler {
    protected static Gson serializer = new Gson();
    protected static Object resBody;
    protected static <T> T getBody(Request request, Class<T> clazz) {
        // parses the request body into an object of any class
        var body = serializer.fromJson(request.body(), clazz);
        if (body == null) {
            throw new RuntimeException("missing required body");
        }
        return body;
    }

    protected static void catchUnauthMess(Response res, DataAccessException e) {
        String message = e.getMessage();
        resBody = Map.of("message", message);
        if (message.equals("Error: unauthorized")) {
            res.status(401);
        } else {
            res.status(500);
        }
    }
}
