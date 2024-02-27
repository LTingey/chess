package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class LoginService {
    static private MemoryUserDAO userDAO = new MemoryUserDAO();
    static private MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public String login(String username, String password) throws DataAccessException {
        UserData existingUser = userDAO.getUser(username);
        if (existingUser == null) {
            throw new DataAccessException("'message': 'Error: unauthorized'");
        }
        if (!existingUser.password().equals(password)) {
            throw new DataAccessException("'message': 'Error: unauthorized'");
        }
        String authToken = authDAO.createAuth(username);
        return authToken;
    }

    public void logout(String authToken) throws DataAccessException {
        AuthData existingAuth = authDAO.getAuth(authToken);
        if (existingAuth == null) {
            throw new DataAccessException("'message': 'Error: unauthorized'");
        }
        authDAO.deleteAuth(existingAuth);
    }
}
