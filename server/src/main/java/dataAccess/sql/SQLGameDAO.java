package dataAccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import dataAccess.GameDAO;
import model.GameData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class SQLGameDAO extends SQLDAO implements GameDAO {

    public int createGame(String gameName) throws DataAccessException {
        return addGame(gameName, new ChessGame());
    }

    public GameData getGame(int gameID) throws DataAccessException {
        String statement = "SELECT id, whiteUsername, blackUsername, gameName, game FROM games WHERE id=?";
        try (var conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setInt(1, gameID);
                try (ResultSet result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        return readGame(result);
                    }
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public HashSet<GameData> listGames() throws DataAccessException {
        HashSet<GameData> games = new HashSet<>();
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT id, whiteUsername, blackUsername, gameName, game FROM games";
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                try (ResultSet result = preparedStatement.executeQuery()) {
                    while (result.next()) {
                        games.add(readGame(result));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return games;
    }

    public int updateGame(GameData game) throws DataAccessException {
        var statement = "DELETE FROM games WHERE id=?";
        executeUpdate(statement, game.gameID());
        int newID = addGame(game.gameName(), game.game());
        return newID;
    }

    public void clear() throws DataAccessException {
        var statement = "TRUNCATE games";
        executeUpdate(statement);
    }

    private int addGame(String gameName, ChessGame game) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO games (gameName, game) VALUES(?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                String jsonGame = new Gson().toJson(game);
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, jsonGame);

                preparedStatement.executeUpdate();

                ResultSet result = preparedStatement.getGeneratedKeys();
                int id = 0;
                if (result.next()) {
                    id = result.getInt(1);
                }
                return id;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    private GameData readGame(ResultSet result) throws SQLException {
        var id = result.getInt("id");
        var whiteUsername = result.getString("whiteUsername");
        var blackUsername = result.getString("blackUsername");
        var gameName = result.getString("gameName");
        var jsonGame = result.getString("game");
        ChessGame game = new Gson().fromJson(jsonGame, ChessGame.class);
        return new GameData(id, whiteUsername, blackUsername, gameName, game);
    }
}
