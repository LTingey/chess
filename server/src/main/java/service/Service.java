package service;

import dataAccess.DataAccessException;
import dataAccess.memory.MemoryAuthDAO;
import dataAccess.memory.MemoryGameDAO;
import dataAccess.memory.MemoryUserDAO;
import model.AuthData;

public class Service {
    protected MemoryUserDAO userDAO = new MemoryUserDAO();
    protected MemoryAuthDAO authDAO = new MemoryAuthDAO();
    protected MemoryGameDAO gameDAO = new MemoryGameDAO();
    protected void checkAuthorization(String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("Error: bad request");
        }
        AuthData existingAuth = authDAO.getAuth(authToken);
        if (existingAuth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
