package service;

import dataAccess.DataAccessException;
import dataAccess.sql.SQLAuthDAO;
import dataAccess.sql.SQLGameDAO;
import dataAccess.sql.SQLUserDAO;
import model.AuthData;

public class Service {
    protected SQLUserDAO userDAO = new SQLUserDAO();
    protected SQLAuthDAO authDAO = new SQLAuthDAO();
    protected SQLGameDAO gameDAO = new SQLGameDAO();
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
