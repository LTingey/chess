package dataAccess;

import model.UserData;

public class MemoryUserDAO extends UserDAO {

    @Override
    public void createUser(UserData newUser) {
        userDatabase.addUser(newUser);
    }

    @Override
    public UserData getUser(String username) {
        return userDatabase.findUser(username);
    }

    @Override
    public void clear() {
        userDatabase.clearUsers();
    }
}
