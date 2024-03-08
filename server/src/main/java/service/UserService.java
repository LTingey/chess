package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import models.LoginRequest;
import models.LoginResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService extends Service {
    public LoginResult register(UserData user) throws DataAccessException {
        // check if a password was provided
        if (user.password() == null) {
            throw new DataAccessException("Error: bad request");
        }
        // check if there is already a user with that username
        UserData existingUser = userDAO.getUser(user.username());
        if (existingUser != null) {
            throw new DataAccessException("Error: already taken");
        }
        userDAO.addUser(user);
        String authToken = authDAO.createAuth(user.username());
        return new LoginResult(user.username(), authToken);
    }

    public LoginResult login(LoginRequest request) throws DataAccessException {
        UserData existingUser = userDAO.getUser(request.username());
        // check that the user exists
        if (existingUser == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        // check that the password matches
        var encoder = new BCryptPasswordEncoder();
        var hashedPassword = encoder.encode(request.password());
        if (!existingUser.password().matches(hashedPassword)) {
            throw new DataAccessException("Error: unauthorized");
        }
        String authToken = authDAO.createAuth(request.username());
        return new LoginResult(request.username(), authToken);
    }

    public void logout(String authToken) throws DataAccessException {
        checkAuthorization(authToken);
        AuthData existingAuth = authDAO.getAuth(authToken);
        authDAO.deleteAuth(existingAuth);
    }
}
