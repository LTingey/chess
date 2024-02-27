package dataAccess;

import model.GameData;

import java.util.HashSet;

public class MemoryGameDAO extends GameDAO{
    @Override
    public void createGame(GameData game) {
        gameDatabase.addGame(game);
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
