package dataAccess;

import database.DatabaseManager;
import model.GameData;

import java.util.HashSet;

abstract public class GameDAO {
    static protected DatabaseManager gameDatabase = new DatabaseManager();

    abstract protected void createGame(GameData game) throws DataAccessException;
    abstract protected GameData getGame(int gameID) throws DataAccessException;
    abstract protected HashSet<GameData> listGames() throws DataAccessException;
    abstract protected void updateGame(GameData game) throws DataAccessException;
    abstract protected void clear();
}
