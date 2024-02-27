package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import models.LoginRequest;
import models.LoginResult;

public class UserService {
    private MemoryUserDAO userDAO = new MemoryUserDAO();
    private MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public LoginResult login(LoginRequest request) throws DataAccessException {
        UserData existingUser = userDAO.getUser(request.username());
        if (existingUser == null) {
            throw new DataAccessException("'message': 'Error: unauthorized'");
        }
        if (!existingUser.password().equals(request.password())) {
            throw new DataAccessException("'message': 'Error: unauthorized'");
        }
        String authToken = authDAO.createAuth(request.username());
        return new LoginResult(request.username(), authToken);
    }

    public void logout(String authToken) throws DataAccessException {
        AuthData existingAuth = authDAO.getAuth(authToken);
        if (existingAuth == null) {
            throw new DataAccessException("'message': 'Error: unauthorized'");
        }
        authDAO.deleteAuth(existingAuth);
    }

    public LoginResult register(UserData user) throws DataAccessException {
        return null;
    }
}
