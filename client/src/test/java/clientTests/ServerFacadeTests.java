package clientTests;

import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ResponseException;
import ui.ServerFacade;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {
    private static Server server;
    static ServerFacade facade;
    private static String port;
    private static String authToken;
    private static String gameID;
    @BeforeAll
    public static void setUp() throws ResponseException {
        server = new Server();
        int p = server.run(0);
        port = Integer.toString(p);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
        facade.clear();

        Map<String, String> user = facade.register("Connor", "donewschool", "connorsemail");
        authToken = user.get("authToken");

        Map<String, String> game = facade.createGame("The Game of Life", authToken);
        gameID = game.get("gameID");
        facade.joinGame(Integer.parseInt(gameID), "white", authToken);
    }

    @AfterAll
    static void tearDown() {
        server.stop();
    }

//    @Test
//    public void clear() throws ResponseException {
//        facade.clear();
//        assertTrue(true);
//    }

    @Test
    public void successRegister() throws ResponseException {
        Map<String, String> user = facade.register("otherRoomie", "hotcrossbuns", "craisins");
        assertTrue(true);
    }

    @Test
    public void usernameTaken() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> facade.register("Connor", "linalgebra", "genius"));
        assertEquals("Server returned HTTP response code: 403 for URL: http://localhost:" + port + "/user",
                exception.getMessage());
    }

    @Test
    public void successLogin() throws ResponseException {
        facade.login("Connor", "donewschool");
        assertTrue(true);
    }

    @Test
    public void wrongPassword() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> facade.login("Connor", "badpassword"));
        assertEquals("Server returned HTTP response code: 401 for URL: http://localhost:" + port + "/session",
                exception.getMessage());
    }

    @Test
    public void successLogout() throws ResponseException {
        facade.logout(authToken);
        assertTrue(true);
    }

    @Test
    public void badAuthTokenLogout() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> facade.logout("notAnAuthToken"));
        assertEquals("Server returned HTTP response code: 401 for URL: http://localhost:" + port + "/session",
                exception.getMessage());
    }

    @Test
    public void successListGames() throws ResponseException {
        Map<String, ArrayList<GameData>> result = facade.listGames(authToken);
        assertEquals(ArrayList.class, result.get("games").getClass());
    }

    @Test
    public void badAuthTokenList() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> facade.listGames("badauthtoken"));
        assertEquals("Server returned HTTP response code: 401 for URL: http://localhost:" + port + "/game",
                exception.getMessage());
    }

    @Test
    public void successCreateGame() throws ResponseException {
        facade.createGame("GameGame", authToken);
        assertTrue(true);
    }

    @Test
    public void badAuthTokenCreate() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> facade.createGame("DumbGame", "dumbauthtoken"));
        assertEquals("Server returned HTTP response code: 401 for URL: http://localhost:" + port + "/game",
                exception.getMessage());
    }

    @Test
    public void successJoinGame() throws ResponseException {
        facade.joinGame(Integer.parseInt(gameID), "black", authToken);
        assertTrue(true);
    }

    @Test
    public void badID() {
        ResponseException exception = assertThrows(ResponseException.class,
                () -> facade.joinGame(-3, "magenta", authToken));
        assertEquals("Server returned HTTP response code: 400 for URL: http://localhost:" + port + "/game",
                exception.getMessage());
    }
}