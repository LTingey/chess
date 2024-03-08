package dataAccess;

import model.GameData;

import java.util.HashSet;

public interface GameDAO {
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    HashSet<GameData> listGames() throws DataAccessException;
    int updateGame(GameData game) throws DataAccessException;
    void clear() throws DataAccessException;
}
