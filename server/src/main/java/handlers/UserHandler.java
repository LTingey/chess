package handlers;

import dataAccess.DataAccessException;
import model.UserData;
import model.LoginRequest;
import model.LoginResult;
import service.UserService;
import spark.*;

import java.util.Map;

public class UserHandler extends Handler {
    static UserService userService = new UserService();

    public static Object register(Request req, Response res) {
        var reqBody = getBody(req, UserData.class);
        Map<String, String> resBody;

        try {
            LoginResult regRes = userService.register(reqBody);
            resBody = Map.of("username", regRes.username(), "authToken", regRes.authToken());
            res.status(200);
        }
        catch (DataAccessException e) {
            String message = e.getMessage();
            resBody = Map.of("message", message);
            switch (message) {
                case "Error: bad request":
                    res.status(400);
                    break;
                case "Error: already taken":
                    res.status(403);
                    break;
                default:
                    res.status(500);
            }
        }

        res.type("application/json");
        return serializer.toJson(resBody);
    }

    public static Object login(Request req, Response res) {
        var reqBody = getBody(req, LoginRequest.class);
        Map<String, String> resBody;

        try {
            LoginResult loginRes = userService.login(reqBody);
            resBody = Map.of("username", loginRes.username(), "authToken", loginRes.authToken());
            res.status(200);
        }
        catch (DataAccessException e) {
            resBody = Map.of("message", e.getMessage());
            if (e.getMessage().equals("Error: unauthorized")) {
                res.status(401);
            } else {
                res.status(500);
            }
        }

        res.type("application/json");
        return serializer.toJson(resBody);
    }

    public static Object logout(Request req, Response res) {
        String reqHeader = req.headers("authorization");
        Map<String, String> resBody = null;

        try {
            userService.logout(reqHeader);
            res.status(200);
        }
        catch (DataAccessException e) {
            String exceptmess = e.getMessage();
            resBody = Map.of("message", exceptmess);
            if (exceptmess.equals("Error: unauthorized")) {
                res.status(401);
            } else {
                res.status(500);
            }
        }

        res.type("application/json");
        return serializer.toJson(resBody);
    }
}
