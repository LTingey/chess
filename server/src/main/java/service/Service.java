package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;

public class Service {
    protected MemoryUserDAO userDAO = new MemoryUserDAO();
    protected MemoryAuthDAO authDAO = new MemoryAuthDAO();
    protected MemoryGameDAO gameDAO = new MemoryGameDAO();
    protected void checkAuthorization(String authToken) throws DataAccessException {
        AuthData existingAuth = authDAO.getAuth(authToken);
        if (existingAuth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
