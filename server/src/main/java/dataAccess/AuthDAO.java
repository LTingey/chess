package dataAccess;

import model.AuthData;


public interface AuthDAO {
    String createAuth(String username) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(AuthData authData) throws DataAccessException;
    void clear() throws DataAccessException;
}