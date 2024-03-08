package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.UserDAO;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO extends SQLDAO implements UserDAO {
    public void addUser(UserData newUser) throws DataAccessException {
        var encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(newUser.password());
        String statement = "INSERT INTO users (username, password, email) VALUES(?, ?, ?)";
        executeUpdate(statement, newUser.username(), hashedPassword, newUser.email());
    }

    public UserData getUser(String usersName) throws DataAccessException {
        String statement = "SELECT username, password, email FROM users WHERE username=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, usersName);
                try (ResultSet result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        var username = result.getString("username");
                        var password = result.getString("password");
                        var email = result.getString("email");
                        return new UserData(username, password, email);
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clear() throws DataAccessException {
        String statement = "TRUNCATE users";
        executeUpdate(statement);
    }
}
