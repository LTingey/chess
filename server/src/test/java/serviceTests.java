import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.GameData;
import model.UserData;
import models.CreateGameRequest;
import models.JoinGameRequest;
import models.LoginRequest;
import models.LoginResult;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import service.ClearService;
import service.GameService;
import service.UserService;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class serviceTests {
    private UserService userService = new UserService();
    private GameService gameService = new GameService();
    private ClearService clearService = new ClearService();
    private String authToken;

    @BeforeEach
    void setUp() throws DataAccessException {
        clearService.clear();

        UserData user1 = new UserData("Lorin", "tingleberry", "email1");
        UserData user2 = new UserData("Spencer", "thebigboi", "email2");
        UserData user3 = new UserData("Ryan", "linerdude", "email3");

        authToken = userService.register(user1).authToken();
        String user2Auth = userService.register(user2).authToken();
        String user3Auth = userService.register(user3).authToken();

        userService.logout(user2Auth);

        CreateGameRequest game1 = new CreateGameRequest(user2Auth, "game1");
        CreateGameRequest game2 = new CreateGameRequest(user3Auth, "game2");
    }

    @Test
    public void successRegister() throws DataAccessException {
        UserData user = new UserData("Mario", "I<3P", "iama@yahoo.com");
        String userAuth = userService.register(user).authToken();
    }

    @Test
    public void nullPassword() throws DataAccessException {
        UserData user = new UserData("Peach", null, "whatisan@email.com");
        DataAccessException exception = assertThrows(DataAccessException.class, () -> userService.register(user));
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    public void successLogin() throws DataAccessException {
        LoginRequest correctLogin = new LoginRequest("Spencer", "thebigboi");
        userService.login(correctLogin);
    }

    @Test
    public void wrongPassword() throws DataAccessException {
        LoginRequest wrongLogin = new LoginRequest("Spencer", "CSgenius");
        DataAccessException exception = assertThrows(DataAccessException.class, () -> userService.login(wrongLogin));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void successLogout() throws DataAccessException {
        userService.logout(authToken);
    }

    @Test
    public void unauthorizedLogout() throws DataAccessException {
        String badToken = "l1g3r";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> userService.logout(badToken));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void successListGames() throws DataAccessException {
        gameService.listGames(authToken);
    }

    @Test
    public void unauthorizedListGames() throws DataAccessException {
        String badToken = "8008135";
        DataAccessException exception = assertThrows(DataAccessException.class, () -> gameService.listGames(badToken));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void successCreateGame() throws DataAccessException {
        CreateGameRequest game = new CreateGameRequest(authToken, "Checkers");
        gameService.createGame(game);
    }

    @Test
    public void unauthorizedCreateGame() throws DataAccessException {
        CreateGameRequest badGame = new CreateGameRequest("abc123", "epicgame");
        DataAccessException exception = assertThrows(DataAccessException.class, () -> gameService.createGame(badGame));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    public void successJoinGame() throws DataAccessException {
        CreateGameRequest game = new CreateGameRequest(authToken, "Checkers");
        int id = gameService.createGame(game);
        JoinGameRequest request = new JoinGameRequest(authToken, "WHITE", id);
        gameService.joinGame(request);
    }

    @Test
    public void colorTaken() throws DataAccessException {
        CreateGameRequest game = new CreateGameRequest(authToken, "Checkers");
        int id = gameService.createGame(game);
        JoinGameRequest request = new JoinGameRequest(authToken, "WHITE", id);
        gameService.joinGame(request);
        DataAccessException exception = assertThrows(DataAccessException.class, () -> gameService.joinGame(request));
        assertEquals("Error: already taken", exception.getMessage());
    }

    @Test
    public void testClear() throws DataAccessException {
        clearService.clear();
    }
}
