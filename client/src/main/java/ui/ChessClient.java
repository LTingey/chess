package ui;

import model.GameData;

import java.util.*;

import static ui.EscapeSequences.*;


public class ChessClient {
    private String url;
    private static ServerFacade server;
    private State state = State.SIGNEDOUT;
    private ArrayList<Integer> gameIDs = new ArrayList<>();
    private String authToken;
    private String currentSquareColor = SET_BG_COLOR_TAN;

    public ChessClient(String url) {
        this.url = url;
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
            case "observe" -> observeGame(parameters);
            case "redraw" -> redrawBoard();
            case "leave" -> leave();
            case "make" -> makeMove(parameters);
            case "resign" -> resign();
            case "highlight" -> legalMoves();
            default -> help();
        };
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    register <USERNAME> <PASSWORD> <EMAIL>
                    login <USERNAME> <PASSWORD>
                    quit
                    help
                    """;
        } else if (state == State.SIGNEDIN) {
            return """
                    create <NAME>
                    list
                    join <ID> [WHITE|BLACK|<empty>]
                    observe <ID>
                    logout
                    quit
                    help
                    """;
        } else {
            return """
                    redraw chess board
                    leave
                    make move <PIECE> <DESTINATION>
                    resign
                    highlight legal moves
                    help
                    """;
        }
    }

    // Prelogin UI
    public String register(String... params) throws ResponseException {
        assertSignedOut();
        if (params.length > 0) {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            Map<String, String> result = server.register(username, password, email);
            if (result == null) {
                return "";
            }
            authToken = result.get("authToken");
            state = State.SIGNEDIN;
            return String.format("Logged in as %s", username);
        } else {
            throw new ResponseException("Expected: <USERNAME> <PASSWORD> <EMAIL>");
        }
    }

    public String login(String... params) throws ResponseException {
        assertSignedOut();
        if (params.length > 0) {
            String username = params[0];
            String password = params[1];
            Map<String, String> result = server.login(username, password);
            if (result == null) {
                return "";
            }
            authToken = result.get("authToken");
            state = State.SIGNEDIN;
            return String.format("Logged in as %s", username);
        } else {
            throw new ResponseException("Expected: <USERNAME> <PASSWORD>");
        }
    }

    // Postlogin UI
    public String logout() throws ResponseException {
        assertSignedIn();
        server.logout(authToken);
        state = State.SIGNEDOUT;
        return "Logged out";
    }

    public String createGame(String... params) throws ResponseException {
        assertSignedIn();
        if (params.length > 0) {
            StringBuilder gameName = new StringBuilder();
            // account for spaces in gameName
            for (String param : params) {
                gameName.append(param).append(" ");
            }
            Map <String, String> result = server.createGame(gameName.toString(), authToken);
            if (result == null) {
                return "";
            }
            String gameID = result.get("gameID");
            return String.format("Created new game %s: %s", gameID, gameName);
        } else {
            throw new ResponseException("Expected: <NAME>");
        }
    }

    public String listGames() throws ResponseException {
        assertSignedIn();
        Map<String, ArrayList<GameData>> result = server.listGames(authToken);
        ArrayList<GameData> games = result.get("games");
        if (games.isEmpty()) {
            return "No games yet.";
        }
        var gameList = new StringBuilder();
        ArrayList<Integer> newList = new ArrayList<>();
        int i = 0;
        for (GameData game : games) {
            String gameInfo = String.format("%d. %s\tPlayers: " + SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE + " %s "
                            + SET_TEXT_COLOR_BLACK + "%s " + RESET_BG_COLOR + SET_TEXT_COLOR_BLUE + "\n",
                            i+1, game.gameName(), game.whiteUsername(), game.blackUsername());
            newList.add(game.gameID());
            gameList.append(gameInfo);
            i += 1;
        }
        gameIDs = newList;
        return gameList.toString();
    }

    public String joinGame(String... params) throws ResponseException {
        assertSignedIn();
        if (params.length > 0) {
            if (params.length == 1) {
                return observeGame(params);
            } else {
                return joinHelper(params[0], params[1]);
            }
        } else {
            throw new ResponseException("Expected: <ID> [WHITE|BLACK|<empty>]");
        }
    }

    public String observeGame(String... params) throws ResponseException {
        if (params.length > 0) {
            return joinHelper(params[0], null);
        } else {
            throw new ResponseException("Expected: <ID>");
        }
    }

    private String joinHelper(String gameID, String playerColor) throws ResponseException {
        Map<String, String> result;
        try {
            // check that the given ID is an int
            int gameNum = Integer.parseInt(gameID);
            int index = gameNum - 1;
            // check that the game exists
            if (index < 0 || index >= gameIDs.size()) {
                return "Game " + gameNum + " does not exist. Please choose a number from the list.";
            }
            result = server.joinGame(gameIDs.get(gameNum-1), playerColor, authToken);
            if (result != null) {
                return "";
            }
            state = State.INGAME;
            // print board
            if (playerColor == null || playerColor.equals("white")) {
                return whiteView();
            } else {
                return blackView();
            }
        } catch (NumberFormatException e) {
            return "Game ID must be a number.";
        }
    }

    // Gameplay UI
    public String redrawBoard() throws ResponseException {
        assertInGame();
        return "";
    }

    public String leave() throws ResponseException {
        assertInGame();
        state = State.SIGNEDIN;
        return "Leaving game";
    }

    public String makeMove(String... params) throws ResponseException {
        // Allow the user to input what move they want to make.
        // The board is updated to reflect the result of the move,
        // and the board automatically updates on all clients involved in the game.
        assertInGame();
        return "";
    }

    public String resign() throws ResponseException {
        // Prompts the user to confirm they want to resign.
        // If they do, the user forfeits the game and the game is over.
        // Does not cause the user to leave the game.
        assertInGame();
        return "";
    }

    public String legalMoves() throws ResponseException {
        // Allows the user to input what piece for which they want to highlight legal moves.
        // The selected piece’s current square and all squares it can legally move to are highlighted.
        // This is a local operation and has no effect on remote users’ screens.
        assertInGame();
        return "";
    }

    // helpers
    private void assertSignedOut() throws ResponseException {
        if (state != State.SIGNEDOUT) {
            throw new ResponseException("You must logout first");
        }
    }

    private void assertSignedIn() throws ResponseException {
        if (state != State.SIGNEDIN) {
            throw new ResponseException("You must login first");
        }
    }

    private void assertInGame() throws ResponseException {
        if (state != State.INGAME) {
            throw new ResponseException("You must join a game first");
        }
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
}