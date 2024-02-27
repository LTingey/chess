package dataAccess;

import database.DatabaseManager;
import model.UserData;

abstract public class UserDAO {
    static protected DatabaseManager userDatabase = new DatabaseManager();

    abstract protected void createUser(UserData newUser) throws DataAccessException;
    abstract protected UserData getUser(String username) throws DataAccessException;
    abstract protected void clear();
}
