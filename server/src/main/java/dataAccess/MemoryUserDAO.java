package dataAccess;

import model.UserData;

public class MemoryUserDAO extends UserDAO {

    @Override
    protected void createUser(UserData newUser) {
        userDatabase.addUser(newUser);
    }

    @Override
    protected UserData getUser(String username) {
        return userDatabase.findUser(username);
    }

    @Override
    protected void clear() {
        userDatabase.clearUsers();
    }
}
