package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.UserDAO;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

public class SQLUserDAO extends SQLDAO implements UserDAO {
    public SQLUserDAO() throws DataAccessException {
        DatabaseManager.configureDatabase();
    }
    public void addUser(UserData newUser) throws DataAccessException {
        var encoder = new BCryptPasswordEncoder();
        var hashedPassword = encoder.encode(newUser.password());
        var statement = "INSERT INTO users (username, password, email) VALUES(?, ?, ?)";
        var id = executeUpdate(statement, newUser.username(), hashedPassword, newUser.email());
    }

    public UserData getUser(String usersName) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM users WHERE username=?";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, usersName);
                try (var result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        var username = result.getString("username");
                        var password = result.getString("password");
                        var email = result.getString("email");
                        return new UserData(username, password, email);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE users";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
