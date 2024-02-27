package dataAccess;

import model.AuthData;

abstract public class AuthDAO {
    static protected DatabaseManager authDatabase = new DatabaseManager();

    abstract protected void createAuth(AuthData newAuth);
    abstract protected AuthData getAuth(String username);
    abstract protected void deleteAuth(AuthData authData);
    abstract protected void clear();
}
