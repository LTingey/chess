package handlers;

import dataAccess.DataAccessException;
import model.GameData;
import model.CreateGameRequest;
import model.JoinGameRequest;
import model.JoinRequestBody;
import service.GameService;
import spark.*;

import java.util.ArrayList;
import java.util.Map;

public class GameHandler extends Handler {
    static GameService gameService = new GameService();
    public static Object listGames(Request req, Response res) {
        String reqHeader = req.headers("authorization");
        Map<String, ArrayList<GameData>> resBody = null;

        try {
            ArrayList<GameData> listRes = gameService.listGames(reqHeader);
            resBody = Map.of("games", listRes);
            res.status(200);
        }
        catch (DataAccessException e) {
            catchUnauthMess(res, e);
        }

        res.type("application/json");
        return serializer.toJson(resBody);
    }

    public static Object createGame(Request req, Response res) throws DataAccessException {
        String reqHeader = req.headers("authorization");
        var reqBody = getBody(req, Map.class);
        Map<String, String> resBody;

        try {
            // check if game name was provided so we can use toString
            if (reqBody.get("gameName") == null) {
                throw new DataAccessException("Error: bad request");
            }
            CreateGameRequest gameRequest = new CreateGameRequest(reqHeader, reqBody.get("gameName").toString());
            int gameID = gameService.createGame(gameRequest);
            resBody = Map.of("gameID", Integer.toString(gameID));
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
        return serializer.toJson(resBody);
    }

    public static Object joinGame(Request req, Response res) {
        String reqHeader = req.headers("authorization");
        var reqBody = getBody(req, JoinRequestBody.class);
        Map<String, String> resBody;
        JoinGameRequest gameReq = new JoinGameRequest(reqHeader, reqBody.playerColor(), reqBody.gameID());

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
        return serializer.toJson(resBody);
    }
}
