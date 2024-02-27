package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import models.CreateGameRequest;
import models.JoinGameRequest;

import java.util.HashSet;

public class GameService {
    private MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private MemoryGameDAO gameDAO = new MemoryGameDAO();

    public HashSet<GameData> listGames(String authToken) throws DataAccessException {
        AuthData userAuth = authDAO.getAuth(authToken);
        if (userAuth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return gameDAO.listGames();
    }

    public int createGame(CreateGameRequest request) throws DataAccessException {
        AuthData userAuth = authDAO.getAuth(request.authToken());
        if (userAuth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        return gameDAO.createGame(request.gameName());
    }

    public void joinGame(JoinGameRequest request) throws DataAccessException {
        AuthData userAuth = authDAO.getAuth(request.authToken());
        if (userAuth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        GameData existingGame = gameDAO.getGame(request.gameID());
        if (existingGame == null) {
            throw new DataAccessException("Error: bad request");
        }

        if (request.playerColor().equals("WHITE")) {
            if (existingGame.whiteUsername() != null) {
                throw new DataAccessException("Error: already taken");
            }
            GameData updatedGame = new GameData(existingGame.gameID(), userAuth.username(), existingGame.blackUsername(), existingGame.gameName(), existingGame.game());
            gameDAO.updateGame(updatedGame);
        }
        else if(request.playerColor().equals("BLACK")) {
            if (existingGame.blackUsername() != null) {
                throw new DataAccessException("Error: already taken");
            }
            GameData updatedGame = new GameData(existingGame.gameID(), existingGame.whiteUsername(), userAuth.username(), existingGame.gameName(), existingGame.game());
            gameDAO.updateGame(updatedGame);
        }
    }
}
