package dataAccess;

import model.GameData;

import java.util.HashSet;

public class MemoryGameDAO extends GameDAO{
    @Override
    protected void createGame(GameData game) {
        gameDatabase.addGame(game);
    }

    @Override
    protected GameData getGame(int gameID) {
        return gameDatabase.findGame(gameID);
    }

    @Override
    protected HashSet<GameData> listGames() {
        return gameDatabase.allGames();
    }

    @Override
    protected void updateGame(GameData game) {
        GameData existingGame = gameDatabase.findGame(game.gameID());

    }

    @Override
    protected void clear() {
        gameDatabase.clearGames();
    }
}
