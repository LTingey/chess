package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashSet;

public class MemoryGameDAO extends GameDAO{
    static private int nextID = 0;              // counter that for unique gameID's

    @Override
    public int createGame(String gameName) {
        GameData game = new GameData(nextID, null, null, gameName, new ChessGame());
        gameDatabase.addGame(game);
        nextID = nextID + 1;
        return game.gameID();
    }

    @Override
    public GameData getGame(int gameID) {
        return gameDatabase.findGame(gameID);
    }

    @Override
    public HashSet<GameData> listGames() {
        return gameDatabase.allGames();
    }

    @Override
    public void updateGame(GameData game) {
        GameData existingGame = gameDatabase.findGame(game.gameID());
        gameDatabase.removeGame(existingGame);
        gameDatabase.addGame(game);
    }

    @Override
    public void clear() {
        gameDatabase.clearGames();
    }
}
