package dataAccess.memory;

import dataAccess.AuthDAO;
import model.AuthData;

import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    MemoryDatabase authDatabase = new MemoryDatabase();

    public String createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, username);
        authDatabase.addAuth(newAuth);
        return authToken;
    }

    public AuthData getAuth(String authToken) {
        return authDatabase.findAuth(authToken);
    }

    public void deleteAuth(AuthData authData) {
        authDatabase.removeAuth(authData);
    }

    public void clear() {
        authDatabase.clearAuths();
    }
}
