package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDAO;
import model.GameData;

import java.util.HashSet;

public class SQLGameDAO implements GameDAO {
    public SQLGameDAO() throws DataAccessException {
        DatabaseManager.configureDatabase();
    }

    public int createGame(String gameName) {
        return 0;
    }

    public GameData getGame(int gameID) {
        return null;
    }

    public HashSet<GameData> listGames() {
        return null;
    }

    public void updateGame(GameData game) {

    }

    public void clear() {

    }
}
