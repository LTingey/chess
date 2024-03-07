package dataAccess.sql;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO extends SQLDAO implements AuthDAO {
    public SQLAuthDAO() throws DataAccessException {
        DatabaseManager.configureDatabase();
    }

    public String createAuth(String username) throws DataAccessException{
        var statement = "INSERT INTO auths (username, authToken) VALUES(?, ?)";
        String authToken = UUID.randomUUID().toString();
        executeUpdate(statement, username, authToken);
        return authToken;
    }

    public AuthData getAuth(String usersName) throws DataAccessException {
        String statement = "SELECT username, authToken FROM auths WHERE username=?";
        try (ResultSet result = getData(statement, usersName)) {
            if (result.next()) {
                var username = result.getString("username");
                var authToken = result.getString("authToken");
                return new AuthData(username, authToken);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    public void deleteAuth(AuthData authData) throws DataAccessException {
        String statement = "DELETE FROM auths WHERE username=?";
        executeUpdate(statement, authData.username());
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auths";
        executeUpdate(statement);
    }
}
