package dataAccess.sql;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO extends SQLDAO implements AuthDAO {

    public String createAuth(String username) throws DataAccessException{
        var statement = "INSERT INTO auths (username, authToken) VALUES(?, ?)";
        String authToken = UUID.randomUUID().toString();
        executeUpdate(statement, username, authToken);
        return authToken;
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        String statement = "SELECT username, authToken FROM auths WHERE authToken=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, authToken);
                try (ResultSet result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        var username = result.getString("authToken");
                        var token = result.getString("username");
                        return new AuthData(username, token);
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void deleteAuth(AuthData authData) throws DataAccessException {
        String statement = "DELETE FROM auths WHERE authToken=?";
        executeUpdate(statement, authData.authToken());
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auths";
        executeUpdate(statement);
    }
}
