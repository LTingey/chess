package dataAccess;

import model.AuthData;

public class MemoryAuthDAO extends AuthDAO{
    @Override
    protected void createAuth(AuthData newAuth) throws DataAccessException {
        authDatabase.addAuth(newAuth);
    }

    @Override
    protected AuthData getAuth(String username) throws DataAccessException {
        return authDatabase.findAuth(username);
    }

    @Override
    protected void deleteAuth(AuthData authData) throws DataAccessException {
        authDatabase.removeAuth(authData);
    }

    @Override
    protected void clear() {
        authDatabase.clearAuths();
    }
}
