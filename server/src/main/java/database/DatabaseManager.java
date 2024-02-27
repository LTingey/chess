package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DatabaseManager {
    HashSet<ArrayList<String>> userDatabase = new HashSet<>();
    HashMap<String, String> authDatabase = new HashMap<>();
    HashSet<ArrayList<String>> gameDatabase = new HashSet<>();

    public ArrayList<String> getUser(String username) {
        for (ArrayList<String> user : userDatabase) {
            if (user.getFirst().equals(username)) {
                return user;
            }
        }
        return new ArrayList<>();
    }

    public void addUser(String username, String password, String email) {
        ArrayList<String> newUser = new ArrayList<>(3);
        newUser.add(username);
        newUser.add(password);
        newUser.add(email);
        userDatabase.add(newUser);
    }

    public String getAuth(String username) {
        for (String user : authDatabase.keySet()) {
            if (user.equals(username)) {
                return authDatabase.get(user);
            }
        }
        return "";
    }

    public void addAuth(String username, String authToken) {
        authDatabase.put(username, authToken);
    }

    public void removeAuth(String username) {
        authDatabase.remove(username);
    }

    public ArrayList<String> getGame(String gameID) {
        for (ArrayList<String> game : gameDatabase) {
            if (game.getFirst().equals(gameID)) {
                return game;
            }
        }
        return new ArrayList<>();
    }

    public void addGame(String gameID, String whiteUsername, String blackUsername, String gameName, String game) {
        ArrayList<String> newGame = new ArrayList<>(5);
        newGame.add(gameID);
        newGame.add(whiteUsername);
        newGame.add(blackUsername);
        newGame.add(gameName);
        newGame.add(game);
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
