package dataAccess.memory;

import dataAccess.UserDAO;
import dataAccess.memory.MemoryDatabase;
import model.UserData;

public class MemoryUserDAO implements UserDAO {
    private static MemoryDatabase userDatabase = new MemoryDatabase();

    public void addUser(UserData newUser) {
        userDatabase.addUser(newUser);
    }

    public UserData getUser(String usersName) {
        return userDatabase.findUser(usersName);
    }

    public void clear() {
        userDatabase.clearUsers();
    }
}
