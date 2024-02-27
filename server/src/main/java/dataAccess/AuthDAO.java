package dataAccess;

import database.DatabaseManager;
import model.AuthData;

abstract public class AuthDAO {
    static protected DatabaseManager authDatabase = new DatabaseManager();

    abstract protected void createAuth(AuthData newAuth) throws DataAccessException;
    abstract protected AuthData getAuth(String username) throws DataAccessException;
    abstract protected void deleteAuth(AuthData authData)throws DataAccessException;
    abstract protected void clear();
}
