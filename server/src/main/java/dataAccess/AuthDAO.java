package dataAccess;

import model.AuthData;

abstract public class AuthDAO {
    static protected DatabaseManager authDatabase = new DatabaseManager();

    abstract public void createAuth(AuthData newAuth);
    abstract public AuthData getAuth(String username);
    abstract public void deleteAuth(AuthData authData);
    abstract public void clear();
}
