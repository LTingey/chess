package dataAccess;

import model.GameData;

import java.util.HashSet;

abstract public class GameDAO {
    static protected DatabaseManager gameDatabase = new DatabaseManager();

    abstract protected void createGame(GameData game);
    abstract protected GameData getGame(int gameID);
    abstract protected HashSet<GameData> listGames();
    abstract protected void updateGame(GameData game);
    abstract protected void clear();
}
