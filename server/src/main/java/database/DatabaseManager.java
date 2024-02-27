package database;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashSet;

public class DatabaseManager {
    HashSet<UserData> userDatabase = new HashSet<>();
    HashSet<AuthData> authDatabase = new HashSet<>();
    HashSet<GameData> gameDatabase = new HashSet<>();

    public UserData findUser(String username) {
        for (UserData user : userDatabase) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(UserData newUser) {
        userDatabase.add(newUser);
    }

    public AuthData findAuth(String username) {
        for (AuthData authData : authDatabase) {
            if (authData.username().equals(username)) {
                return authData;
            }
        }
        return null;
    }

    public void addAuth(AuthData newAuth) {
        authDatabase.add(newAuth);
    }

    public void removeAuth(AuthData authData) {
        authDatabase.remove(authData);
    }

    public GameData findGame(int gameID) {
        for (GameData game : gameDatabase) {
            if (game.gameID() == gameID) {
                return game;
            }
        }
        return null;
    }

    public void addGame(GameData newGame) {
        gameDatabase.add(newGame);
    }


    public HashSet<GameData> allGames() {
        return gameDatabase;
    }

    public void clearUsers() {
        userDatabase.clear();
    }

    public void clearAuths() {
        authDatabase.clear();
    }

    public void clearGames() {
        gameDatabase.clear();
    }
}
