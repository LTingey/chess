package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.UserDAO;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserDAO implements UserDAO {
    public SQLUserDAO() throws DataAccessException {
        DatabaseManager.configureDatabase();
    }
    public void createUser(UserData newUser) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO users (username, password, email) VALUES(?, ?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                var encoder = new BCryptPasswordEncoder();
                var hashedPassword = encoder.encode(newUser.password());
                preparedStatement.setString(1, newUser.username());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, newUser.email());

                preparedStatement.executeUpdate();

                var idIter = preparedStatement.getGeneratedKeys();
                var id = 0;
                if (idIter.next()) {
                    id = idIter.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
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
