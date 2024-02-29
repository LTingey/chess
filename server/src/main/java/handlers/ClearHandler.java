package handlers;

import com.google.gson.Gson;
import service.ClearService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class ClearHandler extends Handler {
    static ClearService clearService = new ClearService();

    public static Object clear(Request req, Response res) {
        Map<String, String> resBody = null;

        try {
            clearService.clear();
            res.status(200);
        }
        catch (Exception e) {
            resBody = Map.of("message", e.getMessage());
            res.status(500);
        }

        res.type("application/json");
        return new Gson().toJson(resBody);
    }
}
