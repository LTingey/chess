import chess.*;
import server.Server;
import ui.Repl;

public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        Server server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);

        new Repl(serverUrl).run();

        server.stop();
    }
}