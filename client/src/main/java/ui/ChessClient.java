package ui;

import model.GameData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static ui.EscapeSequences.*;


public class ChessClient {
//    private String url;                       for Websockets
    private static ServerFacade server;
    private State state = State.SIGNEDOUT;
    private HashMap<Integer, Integer> gameIDs = new HashMap<>();
    private String authToken;
    private String currentSquareColor = SET_BG_COLOR_TAN;

    public ChessClient(String url) {
//        this.url = url;
        server = new ServerFacade(url);
    }

    public State getState() {
        return state;
    }

    public String evalInput(String input) throws ResponseException {
        String[] tokens = input.toLowerCase().split(" ");   // puts each word of the input into an array
        String option = "";
        if (tokens.length > 0) {
            option = tokens[0];
        }
        String[] parameters = Arrays.copyOfRange(tokens, 1, tokens.length);


        return switch (option) {
            case "quit" -> "Goodbye!";
            case "login" -> login(parameters);
            case "register" -> register(parameters);
            case "logout" -> logout();
            case "create" -> createGame(parameters);
            case "list" -> listGames();
            case "join" -> joinGame(parameters);
            case "observe" -> joinObserver(parameters);
            default -> help();
        };
    }

    public String help() {
        // Displays text informing the user what actions they can take
        if (state == State.SIGNEDOUT) {
            return """
                    register <USERNAME> <PASSWORD> <EMAIL>
                    login <USERNAME> <PASSWORD>
                    quit
                    help
                    """;
        } else {
            return """
                    create <NAME>
                    list
                    join <ID> [WHITE|BLACK|<empty>]
                    observe <ID>
                    logout
                    quit
                    help
                    """;
        }
    }

    // Prelogin UI
    public String login(String... params) throws ResponseException {
        // Prompts the user to input login information
        // Calls the server login API to login the user
        // When successfully logged in, the client should transition to the Postlogin UI
        if (params.length > 0) {
            String username = params[0];
            String password = params[1];
            Map<String, String> result = server.login(username, password);
            authToken = result.get("authToken");
            state = State.SIGNEDIN;
            return String.format("Logged in as %s", username);
        } else {
            throw new ResponseException("Expected: <USERNAME> <PASSWORD>");
        }
    }

    public String register(String... params) throws ResponseException {
        // Prompts the user to input registration information
        // Calls the server register API to register and login the user
        // If successfully registered, the client should be logged in and transition to the Postlogin UI
        if (params.length > 0) {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            Map<String, String> result = server.register(username, password, email);
            authToken = result.get("authToken");
            state = State.SIGNEDIN;
            return String.format("Logged in as %s", username);
        } else {
            throw new ResponseException("Expected: <USERNAME> <PASSWORD> <EMAIL>");
        }
    }

    // Postlogin UI
    public String logout() throws ResponseException {
        // Logs out the user
        // Calls the server logout API to logout the user
        // After logging out with the server, the client should transition to the Prelogin UI
        assertSignedIn();
        String message = "";
        Map<String, String> result = server.logout(authToken);
        if (result != null) {
            message = result.get("message");
        }
        state = State.SIGNEDOUT;
        return message;
    }

    public String createGame(String... params) throws ResponseException {
        // Allows the user to input a name for the new game
        // Calls the server create API to create the game
        // This does not join the player to the created game; it only creates the new game in the server
        assertSignedIn();
        if (params.length > 0) {
            String gameName = params[0];
            Map <String, String> result = server.createGame(gameName, authToken);

            if (result.get("message") != null) {
                return result.get("message");
            }

            String gameID = result.get("gameID");
            return String.format("Created new game %s: %s", gameID, gameName);
        } else {
            throw new ResponseException("Expected: <NAME>");
        }
    }

    public String listGames() throws ResponseException {
        // Lists all the games that currently exist on the server
        // Calls the server list API to get all the game data, and displays the games in a numbered list,
        //      including the game name and players (not observers) in the game
        // The numbering for the list should be independent of the game IDs
        assertSignedIn();
        Map<String, Object> result = server.listGames(authToken);

        if (result.get("message") != null) {
            return (String) result.get("message");
        }

        HashSet<GameData> games = (HashSet<GameData>) result.get("games");
        var gameList = new StringBuilder();
        HashMap<Integer, Integer> newList = new HashMap<>();
        int i = 1;
        for (GameData game : games) {
            String gameInfo = String.format("%d. %s\tPlayers: " + SET_BG_COLOR_WHITE + "%s" + RESET_BG_COLOR + " " + SET_BG_COLOR_BLACK + "%s\n",
                    i, game.gameName(), game.whiteUsername(), game.blackUsername());
            newList.put(i, game.gameID());
            gameList.append(gameInfo);
            i += 1;
        }
        gameIDs = newList;
        return gameList.toString();
    }

    public String joinGame(String... params) throws ResponseException {
        // Allows the user to specify which game they want to join and what color they want to play
        // They should be able to enter the number of the desired game
        // Your client will need to keep track of which number corresponds to which game from the last time it listed the games
        // Calls the server join API to join the user to the game
        assertSignedIn();
        if (params.length > 0) {
            Map<String, String> result;
            if (params.length == 1) {
                return joinObserver(params);
            } else {
                int gameID = Integer.parseInt(params[0]);
                String color = params[1];
                result = server.joinGame(gameID, color, authToken);
            }

            if (result != null) {
                return result.get("message");
            }

            // print board
            return makeBoards();
        } else {
            throw new ResponseException("Expected: <ID> [WHITE|BLACK|<empty>]");
        }
    }

    public String joinObserver(String... params) throws ResponseException {
        // Allows the user to specify which game they want to observe
        // They should be able to enter the number of the desired game
        // Your client will need to keep track of which number corresponds to which game from the last time it listed the games
        // Calls the server join API to verify that the specified game exists
        int gameID = Integer.parseInt(params[0]);
        Map<String, String> result = server.joinGame(gameID, null, authToken);
        if (result != null) {
            return result.get("message");
        }
        return makeBoards();
    }

    private String makeBoards() {
        var output = new StringBuilder();
        output.append(whiteView()).append("\n\n").append(blackView());
        return output.toString();
    }

    private String whiteView() {
        var board = new StringBuilder();
        // iterate through each row, starting at the top
        for (int i=8; i>0; i--) {
            board.append(SET_TEXT_COLOR_LIGHT_GREY).append(i).append(" ");
            String rowString = "";
            if (i == 8) {
                rowString = whiteViewFirstRows(SET_TEXT_COLOR_BLACK);
            } else if (i == 1) {
                rowString = whiteViewFirstRows(SET_TEXT_COLOR_WHITE);
            } else if (i == 7) {
                rowString = secondRows(SET_TEXT_COLOR_BLACK);
            } else if (i == 2) {
                rowString = secondRows(SET_TEXT_COLOR_WHITE);
            } else {
                rowString = middleRows();
            }
            board.append(rowString).append("\n");
        }

        String[] labels = {"A", "B", "C", "D", "E", "F", "G", "H"};
        board.append(bottomLabels(labels));

        return board.toString();
    }

    private String blackView() {
        var board = new StringBuilder();

        for (int i=1; i<9; i++) {
            board.append(SET_TEXT_COLOR_LIGHT_GREY).append(i).append(" ");
            String rowString = "";
            if (i == 8) {
                rowString = blackViewFirstRows(SET_TEXT_COLOR_BLACK);
            } else if (i == 1) {
                rowString = blackViewFirstRows(SET_TEXT_COLOR_WHITE);
            } else if (i == 7) {
                rowString = secondRows(SET_TEXT_COLOR_BLACK);
            } else if (i == 2) {
                rowString = secondRows(SET_TEXT_COLOR_WHITE);
            } else {
                rowString = middleRows();
            }
            board.append(rowString).append("\n");
        }

        String[] labels = {"H", "G", "F", "E", "D", "C", "B", "A"};
        board.append(bottomLabels(labels));

        return board.toString();
    }

    private String whiteViewFirstRows(String pieceColor) {
        String rowString = pieceColor + nextSquareColor(true) + ROOK + nextSquareColor(false) + KNIGHT
                + nextSquareColor(false) + BISHOP + nextSquareColor(false) + QUEEN
                + nextSquareColor(false) + KING + nextSquareColor(false) + BISHOP
                + nextSquareColor(false) + KNIGHT + nextSquareColor(false) + ROOK + RESET_BG_COLOR;
        return rowString;
    }

    private String blackViewFirstRows(String pieceColor) {
        String rowString = pieceColor + nextSquareColor(true) + ROOK + nextSquareColor(false) + KNIGHT
                + nextSquareColor(false) + BISHOP + nextSquareColor(false) + KING
                + nextSquareColor(false) + QUEEN + nextSquareColor(false) + BISHOP
                + nextSquareColor(false) + KNIGHT + nextSquareColor(false) + ROOK + RESET_BG_COLOR;
        return rowString;
    }

    private String secondRows(String pieceColor) {
        String rowString = pieceColor + nextSquareColor(true) + PAWN + nextSquareColor(false) + PAWN
                + nextSquareColor(false) + PAWN + nextSquareColor(false) + PAWN
                + nextSquareColor(false) + PAWN + nextSquareColor(false) + PAWN
                + nextSquareColor(false) + PAWN + nextSquareColor(false) + PAWN + RESET_BG_COLOR;
        return rowString;
    }

    private String middleRows() {
        String rowString = nextSquareColor(true) + EM_SPACE + nextSquareColor(false) + EM_SPACE
                + nextSquareColor(false) + EM_SPACE + nextSquareColor(false) + EM_SPACE
                + nextSquareColor(false) + EM_SPACE + nextSquareColor(false) + EM_SPACE
                + nextSquareColor(false) + EM_SPACE + nextSquareColor(false) + EM_SPACE
                + RESET_BG_COLOR;
        return rowString;
    }

    private String bottomLabels(String[] labels) {
        var labelsString = new StringBuilder();
        labelsString.append(SET_TEXT_COLOR_LIGHT_GREY).append(EN_SPACE);
        for (String label : labels) {
            labelsString.append(SIXTH_EM_SPACE).append(label).append(SIXTH_EM_SPACE);
        }
        return labelsString.toString();
    }

    private String nextSquareColor(boolean newLine) {
        if (newLine) {
            return currentSquareColor;
        } else {
            if (currentSquareColor.equals(SET_BG_COLOR_BROWN)) {
                currentSquareColor = SET_BG_COLOR_TAN;
                return SET_BG_COLOR_TAN;
            } else {
                currentSquareColor = SET_BG_COLOR_BROWN;
                return SET_BG_COLOR_BROWN;
            }
        }
    }

    private void assertSignedIn() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            throw new ResponseException("You must login first");
        }
    }
}