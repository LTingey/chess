package dataAccess;

import model.UserData;

public interface UserDAO {
    void createUser(UserData newUser) throws DataAccessException;
    UserData getUser(String usersName) throws DataAccessException;
    void clear() throws DataAccessException;
}
