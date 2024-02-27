package dataAccess;

import model.UserData;

abstract public class UserDAO {
    static protected DatabaseManager userDatabase = new DatabaseManager();

    abstract protected void createUser(UserData newUser);
    abstract protected UserData getUser(String username);
    abstract protected void clear();
}
