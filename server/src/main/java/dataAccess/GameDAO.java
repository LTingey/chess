package dataAccess;

import model.GameData;

import java.util.HashSet;

abstract public class GameDAO {
    static protected DatabaseManager gameDatabase = new DatabaseManager();

    abstract public void createGame(GameData game);
    abstract public GameData getGame(int gameID);
    abstract public HashSet<GameData> listGames();
    abstract public void updateGame(GameData game);
    abstract public void clear();
}
