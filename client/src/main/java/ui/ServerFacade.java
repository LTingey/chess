package ui;

import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import com.google.gson.Gson;

public class ServerFacade {
    // makes the HTTP requests and sends them to server
    private String url;

    public ServerFacade(String url) {
        this.url = url;
    }

    public void clear() throws ResponseException {
        var path = "/db";
        makeRequest("DELETE", path, null, "", Map.class);
    }

    public Map<String, String> register(String username, String password, String email) throws ResponseException {
        var path = "/user";
        var reqBody = new UserData(username, password, email);
        var resBody = makeRequest("POST", path, reqBody, "", Map.class);
        return resBody;
    }

    public Map<String, String> login(String username, String password) throws ResponseException {
        var path = "/session";
        var reqBody = new LoginRequest(username, password);
        var resBody = makeRequest("POST", path, reqBody, "", Map.class);
        return resBody;
    }

    public Map<String, String> logout(String authToken) throws ResponseException {
        var path = "/session";
        var resBody = makeRequest("DELETE", path, null, authToken, Map.class);
        return resBody;
    }

    public Map<String, ArrayList<GameData>> listGames(String authToken) throws ResponseException {
        var path = "/game";
        Type type = new TypeToken<Map<String, ArrayList<GameData>>>() {}.getType();
        var resBody = makeRequest("GET", path, null, authToken, type);
        return (Map<String, ArrayList<GameData>>) resBody;
    }

    public Map<String, String> createGame(String gameName, String authToken) throws ResponseException {
        var path = "/game";
        var reqBody = new CreateGameRequest(authToken, gameName);
        var resBody = makeRequest("POST", path, reqBody, authToken, Map.class);
        return resBody;
    }

    public Map<String, String> joinGame(int gameID, String playerColor, String authToken) throws ResponseException {
        var path = "/game";
        var reqBody = new JoinRequestBody(playerColor, gameID);
        var resBody = makeRequest("PUT", path, reqBody, authToken, Map.class);
        return resBody;
    }

    private <T> T makeRequest(String method, String path, Object requestBody, String authToken, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(this.url + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            // write header
            if (!authToken.isEmpty()) {
                http.addRequestProperty("authorization", authToken);
            }
            // write body
            if (requestBody != null) {
                writeBody(requestBody, http);
            }

            http.connect();
            return readBody(http, responseClass);

        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    private <T> T makeRequest(String method, String path, Object requestBody, String authToken, Type type) throws ResponseException {
        try {
            URL url = (new URI(this.url + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            // write header
            if (!authToken.isEmpty()) {
                http.addRequestProperty("authorization", authToken);
            }
            // write body
            if (requestBody != null) {
                writeBody(requestBody, http);
            }

            http.connect();
            return readBody(http, type);

        } catch (Exception ex) {
            throw new ResponseException(ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private static <T> T readBody(HttpURLConnection http, Type type) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (type != null) {
                    response = new Gson().fromJson(reader, type);
                }
            }
        }
        return response;
    }
}
