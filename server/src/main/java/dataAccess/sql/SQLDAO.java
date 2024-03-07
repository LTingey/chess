package dataAccess.sql;

import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLDAO {
    protected void executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    protected ResultSet getData(String statement, String match) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, match);
                try (ResultSet result = preparedStatement.executeQuery()) {
                    return result;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
