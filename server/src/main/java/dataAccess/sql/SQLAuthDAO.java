package dataAccess.sql;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;

public class SQLAuthDAO implements AuthDAO {
    public SQLAuthDAO() throws DataAccessException {
        DatabaseManager.configureDatabase();
    }

    public String createAuth(String username) {
        return null;
    }

    public AuthData getAuth(String username) {
        return null;
    }

    public void deleteAuth(AuthData authData) {

    }

    public void clear() {

    }
}
