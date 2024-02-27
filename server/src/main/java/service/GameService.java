package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import models.CreateGameRequest;
import models.JoinGameRequest;
import models.LoginRequest;

import java.util.HashSet;

public class GameService {
    private MemoryUserDAO userDAO = new MemoryUserDAO();
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
        if (request.playerColor() != null) {

        }
    }
}
