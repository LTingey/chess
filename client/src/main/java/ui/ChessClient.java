package ui;


public class ChessClient {
    private String url;
    private ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ChessClient(String url) {
        this.url = url;
        server = new ServerFacade(url);
    }

    public String evalInput(String input) {
        return null;
    }

    // Prelogin UI
    public String preloginHelp() {
        return null;
    }

    public String quit() {
        return null;
    }

    public String login() {
        return null;
    }

    public String register() {
        return null;
    }

    // Postlogin UI
    public String postloginHelp() {
        return null;
    }

    public String logout() {
        return null;
    }

    public String createGame() {
        return null;
    }

    public String listGames() {
        return null;
    }

    public String joinGame() {
        return null;
    }

    public String joinObserver() {
        return null;
    }
}