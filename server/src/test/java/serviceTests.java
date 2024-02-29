import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import models.LoginResult;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import service.ClearService;
import service.GameService;
import service.UserService;

public class serviceTests {
    private MemoryUserDAO userDAO = new MemoryUserDAO();
    private MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private MemoryGameDAO gameDAO = new MemoryGameDAO();
    private UserService userService = new UserService();
    private GameService gameService = new GameService();
    private ClearService clearService = new ClearService();

    @Test
    public void successRegister() throws TestException {
        UserData user = new UserData("Mario", "I<3P", "iama@yahoo.com");
    }

    @Test
    public void usernameTaken() {

    }

    @Test
    public void successLogin() {

    }

    @Test
    public void wrongPassword() {

    }

    @Test
    public void successLogout() {

    }

    @Test
    public void invalidAuthToken() {

    }

    @Test
    public void successListGames() {

    }

    @Test
    public void successCreateGame() {

    }

    @Test
    public void successJoinGame() {

    }

    @Test
    public void colorTaken() {

    }

    @Test
    public void testClear() {

    }
}
