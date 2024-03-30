package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.CreateGameRequest;
import model.JoinGameRequest;

import java.util.ArrayList;
import java.util.HashSet;

public class GameService extends Service {
    public ArrayList<GameData> listGames(String authToken) throws DataAccessException {
        checkAuthorization(authToken);
        var gameList = new ArrayList<>(gameDAO.listGames());
        return gameList;
    }

    public int createGame(CreateGameRequest request) throws DataAccessException {
        checkAuthorization(request.authToken());
        return gameDAO.createGame(request.gameName());
    }

    public void joinGame(JoinGameRequest request) throws DataAccessException {
        checkAuthorization(request.authToken());
        GameData existingGame = gameDAO.getGame(request.gameID());
        // check if the game exists
        if (existingGame == null) {
            throw new DataAccessException("Error: bad request");
        }

        AuthData userAuth = authDAO.getAuth(request.authToken());
        if (request.playerColor() != null) {
            // if a color is specified, add the caller as the requested color to the game
            if (request.playerColor().equals("white")) {
                if (existingGame.whiteUsername() != null) {
                    throw new DataAccessException("Error: already taken");
                }
                GameData updatedGame = new GameData(existingGame.gameID(), userAuth.username(), existingGame.blackUsername(), existingGame.gameName(), existingGame.game());
                gameDAO.updateGame(updatedGame);
            } else if (request.playerColor().equals("black")) {
                if (existingGame.blackUsername() != null) {
                    throw new DataAccessException("Error: already taken");
                }
                GameData updatedGame = new GameData(existingGame.gameID(), existingGame.whiteUsername(), userAuth.username(), existingGame.gameName(), existingGame.game());
                gameDAO.updateGame(updatedGame);
            }
        }
    }
}
