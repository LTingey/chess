package dataAccess;

import model.UserData;

public interface UserDAO {
    void addUser(UserData newUser) throws DataAccessException;
    UserData getUser(String usersName) throws DataAccessException;
    void clear() throws DataAccessException;
}
