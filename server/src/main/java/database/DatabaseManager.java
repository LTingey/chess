package database;

import model.AuthData;
import model.GameData;
import model.UserData;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DatabaseManager {
    HashSet<UserData> userDatabase = new HashSet<>();
    HashSet<AuthData> authDatabase = new HashSet<>();
    HashSet<GameData> gameDatabase = new HashSet<>();

    public UserData getUser(String username) {
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

    public String getAuth(String username) {
        for (AuthData authData : authDatabase) {
            if (authData.username().equals(username)) {
                return authData.authToken();
            }
        }
        return "";
    }

    public void addAuth(AuthData newAuth) {
        authDatabase.add(newAuth);
    }

    public void removeAuth(AuthData authData) {
        authDatabase.remove(authData);
    }

    public GameData getGame(int gameID) {
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
