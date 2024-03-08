package dataAccess.memory;

import chess.ChessGame;
import dataAccess.GameDAO;
import model.GameData;

import java.util.HashSet;

public class MemoryGameDAO implements GameDAO {
    MemoryDatabase gameDatabase = new MemoryDatabase();
    static private int nextID = 0;              // counter that for unique gameID's

    public int createGame(String gameName) {
        GameData game = new GameData(nextID, null, null, gameName, new ChessGame());
        gameDatabase.addGame(game);
        nextID = nextID + 1;
        return game.gameID();
    }

    public GameData getGame(int gameID) {
        return gameDatabase.findGame(gameID);
    }

    public HashSet<GameData> listGames() {
        return gameDatabase.allGames();
    }

    public void updateGame(GameData game) {
        GameData existingGame = gameDatabase.findGame(game.gameID());
        gameDatabase.removeGame(existingGame);
        gameDatabase.addGame(game);
    }

    public void clear() {
        gameDatabase.clearGames();
    }
}
