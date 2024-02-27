package dataAccess;

import model.AuthData;

import java.util.UUID;

public class MemoryAuthDAO extends AuthDAO{
    @Override
    public String createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        AuthData newAuth = new AuthData(authToken, username);
        authDatabase.addAuth(newAuth);
        return authToken;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return authDatabase.findAuth(authToken);
    }

    @Override
    public void deleteAuth(AuthData authData) {
        authDatabase.removeAuth(authData);
    }

    @Override
    public void clear() {
        authDatabase.clearAuths();
    }
}
