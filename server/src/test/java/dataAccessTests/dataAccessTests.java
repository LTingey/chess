package dataAccessTests;

import chess.ChessBoard;
import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.sql.SQLAuthDAO;
import dataAccess.sql.SQLGameDAO;
import dataAccess.sql.SQLUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class dataAccessTests {
    private SQLUserDAO userDAO = new SQLUserDAO();
    private SQLAuthDAO authDAO = new SQLAuthDAO();
    private SQLGameDAO gameDAO = new SQLGameDAO();
    private int gameID;
    private String authToken;

    @BeforeEach
    void setUp() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();

        UserData user1 = new UserData("Lorin", "tingleberry", "email1");
        UserData user2 = new UserData("Spencer", "thebigboi", "email2");
        UserData user3 = new UserData("Ryan", "linerdude", "email3");

        userDAO.addUser(user1);
        userDAO.addUser(user2);
        userDAO.addUser(user3);

        authToken = authDAO.createAuth("Lorin");

        gameID = gameDAO.createGame("The Game of Love");
        gameDAO.createGame("Wizard's Chess");
    }

    @Test
    public void successAddUser() throws DataAccessException {
        var newUser = new UserData("liger", "thisisagoodpassword", "notmyrealemail@email.email");
        userDAO.addUser(newUser);
    }

    @Test
    public void nullUserData() {
        var badUser = new UserData(null, null, null);
        assertThrows(Exception.class, () -> userDAO.addUser(badUser));
    }

    @Test
    public void successGetUser() throws DataAccessException {
        userDAO.getUser("Ryan");
    }

    @Test
    public void wrongUsername() throws DataAccessException {
        UserData nonUser = userDAO.getUser("Cody");
        assertNull(nonUser);
    }

    @Test
    public void successCreateAuth() throws DataAccessException {
        authDAO.createAuth("Spencer");
    }

    @Test
    public void nullAuthData() {
        assertThrows(Exception.class, () -> authDAO.createAuth(null));
    }

    @Test
    public void successGetAuth() throws DataAccessException {
        authDAO.getAuth(authToken);
    }

    @Test
    public void badAuthToken() throws DataAccessException {
        AuthData badAuth = authDAO.getAuth("urmom");
        assertNull(badAuth);
    }

    @Test
    public void successDeleteAuth() throws DataAccessException {
        AuthData authData = new AuthData(authToken, "Lorin");
        authDAO.deleteAuth(authData);
    }


    @Test
    public void successCreateGame() throws DataAccessException {
        gameDAO.createGame("Game on");
    }

    @Test
    public void nullGameData() {
        assertThrows(Exception.class, () -> gameDAO.createGame(null));
    }

    @Test
    public void successGetGame() throws DataAccessException {
        gameDAO.getGame(gameID);
    }

    @Test
    public void badID() throws DataAccessException {
        GameData nullGame = gameDAO.getGame(-3);
        assertNull(nullGame);
    }

    @Test
    public void successListGames() throws DataAccessException {
        gameDAO.listGames();
    }

    @Test
    public void successUpdateGame() throws DataAccessException {
        int gameID = gameDAO.createGame("Ender's Game");
        GameData myGame = gameDAO.getGame(gameID);
        GameData gameData = new GameData(gameID, "Paul", "Simon", "Ender's Game", myGame.game());
        gameDAO.updateGame(gameData);
    }

    @Test
    public void successUsersClear() throws DataAccessException {
        userDAO.clear();
    }

    @Test
    public void successAuthsClear() throws DataAccessException {
        authDAO.clear();
    }

    @Test
    public void successGamesClear() throws DataAccessException {
        gameDAO.clear();
    }
}
