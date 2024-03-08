package service;

import dataAccess.DataAccessException;

public class ClearService extends Service {
    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}
