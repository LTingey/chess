package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import models.CreateGameRequest;
import models.JoinGameRequest;

import java.util.HashSet;

public class GameService extends Service {
    public HashSet<GameData> listGames(String authToken) throws DataAccessException {
        checkAuthorization(authToken);
        return gameDAO.listGames();
    }

    public int createGame(CreateGameRequest request) throws DataAccessException {
        checkAuthorization(request.authToken());
        return gameDAO.createGame(request.gameName());
    }

    public int joinGame(JoinGameRequest request) throws DataAccessException {
        checkAuthorization(request.authToken());
        GameData existingGame = gameDAO.getGame(request.gameID());
        // check if the game exists
        if (existingGame == null) {
            throw new DataAccessException("Error: bad request");
        }

        AuthData userAuth = authDAO.getAuth(request.authToken());
        if (request.playerColor() != null) {
            int newID;
            // if a color is specified, add the caller as the requested color to the game
            if (request.playerColor().equals("WHITE")) {
                if (existingGame.whiteUsername() != null) {
                    throw new DataAccessException("Error: already taken");
                }
                GameData updatedGame = new GameData(existingGame.gameID(), userAuth.username(), existingGame.blackUsername(), existingGame.gameName(), existingGame.game());
                newID = gameDAO.updateGame(updatedGame);
            } else {
                if (existingGame.blackUsername() != null) {
                    throw new DataAccessException("Error: already taken");
                }
                GameData updatedGame = new GameData(existingGame.gameID(), existingGame.whiteUsername(), userAuth.username(), existingGame.gameName(), existingGame.game());
                newID = gameDAO.updateGame(updatedGame);
            }
            return newID;
        }
        return request.gameID();
    }
}
