package dataAccess;

import model.AuthData;

public class MemoryAuthDAO extends AuthDAO{
    @Override
    public void createAuth(AuthData newAuth) {
        authDatabase.addAuth(newAuth);
    }

    @Override
    public AuthData getAuth(String username) {
        return authDatabase.findAuth(username);
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
