package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private ChessClient client;

    public Repl(String url) {
        client = new ChessClient(url);
    }

    public void run() {
        System.out.println(SET_TEXT_COLOR_BLUE + "Welcome to CS240 Chess. Type Help to get started.");
        Scanner scanner = new Scanner(System.in);

        String output = "";
        while (!output.equals("Goodbye!")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                output = client.evalInput(line);
                System.out.print(output + RESET_TEXT_COLOR + RESET_BG_COLOR);
            } catch (ResponseException e) {
                String message = SET_TEXT_COLOR_RED + e.getMessage() + "\n";
                System.out.print(message);
            }
        }
    }

    private void printPrompt() {
        String state = client.getState().toString();
        System.out.print(SET_TEXT_COLOR_LIGHT_GREY + "\n[" + state + "] >>> " + SET_TEXT_COLOR_BLUE);
    }
}
