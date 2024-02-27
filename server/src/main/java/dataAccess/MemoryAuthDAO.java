package dataAccess;

import model.AuthData;

public class MemoryAuthDAO extends AuthDAO{
    @Override
    protected void createAuth(AuthData newAuth) {
        authDatabase.addAuth(newAuth);
    }

    @Override
    protected AuthData getAuth(String username) {
        return authDatabase.findAuth(username);
    }

    @Override
    protected void deleteAuth(AuthData authData) {
        authDatabase.removeAuth(authData);
    }

    @Override
    protected void clear() {
        authDatabase.clearAuths();
    }
}
