package dataAccess;

import model.UserData;

abstract public class UserDAO {
    static protected DatabaseManager userDatabase = new DatabaseManager();

    abstract public void createUser(UserData newUser);
    abstract public UserData getUser(String username);
    abstract public void clear();
}
