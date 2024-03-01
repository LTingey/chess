package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.GameData;
import models.CreateGameRequest;
import models.JoinGameRequest;
import service.GameService;
import spark.*;

import java.util.HashSet;
import java.util.Map;

public class GameHandler extends Handler {
    static GameService gameService = new GameService();
    public static Object listGames(Request req, Response res) {
        String reqHeader = req.headers("authorization");
        Map<String, Object> resBody;

        try {
            HashSet<GameData> listRes = gameService.listGames(reqHeader);
            resBody = Map.of("games", listRes);
            res.status(200);
        }
        catch (DataAccessException e) {
            String message = e.getMessage();
            resBody = Map.of("message", message);
            if (message.equals("Error: unauthorized")) {
                res.status(401);
            } else {
                res.status(500);
            }
        }

        res.type("application/json");
        return new Gson().toJson(resBody);
    }

    public static Object createGame(Request req, Response res) {
        String reqHeader = req.headers("authorization");
        var reqBody = getBody(req, Map.class);
        Map<String, Object> resBody;
        CreateGameRequest gameRequest = new CreateGameRequest(reqHeader, reqBody.get("gameName").toString());

        try {
            int gameID = gameService.createGame(gameRequest);
            resBody = Map.of("gameID", gameID);
            res.status(200);
        }
        catch (DataAccessException e) {
            String message = e.getMessage();
            resBody = Map.of("message", message);
            switch (message) {
                case "Error: bad request":
                    res.status(400);
                    break;
                case "Error: unauthorized":
                    res.status(401);
                    break;
                default:
                    res.status(500);
            }
        }

        res.type("application/json");
        return new Gson().toJson(resBody);
    }

    public static Object joinGame(Request req, Response res) {
        String reqHeader = req.headers("authorization");
        var reqBody = getBody(req, Map.class);
        Object resBody;
        JoinGameRequest gameReq = new JoinGameRequest(reqHeader, reqBody.get("playerColor").toString(), Integer.parseInt(reqBody.get("gameID").toString()));

        try {
            gameService.joinGame(gameReq);
            resBody = null;
            res.status(200);
        }
        catch (DataAccessException e) {
            String message = e.getMessage();
            resBody = Map.of("message", message);
            switch (message) {
                case "Error: bad request":
                    res.status(400);
                    break;
                case "Error: unauthorized":
                    res.status(401);
                    break;
                case "Error: already taken":
                    res.status(403);
                    break;
                default:
                    res.status(500);
            }
        }

        res.type("application/json");
        return new Gson().toJson(resBody);
    }
}