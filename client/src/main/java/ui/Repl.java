package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private ChessClient client;

    public Repl(String url) {
        client = new ChessClient(url);
    }

    public void run() {
        System.out.println("Welcome to CS240 Chess. Type Help to get started.");
        Scanner scanner = new Scanner(System.in);

        String output = "";
        while (!output.equals("Quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                output = client.evalInput(line);
                System.out.print(SET_TEXT_COLOR_BLUE);
            } catch (Throwable e) {
                String message = e.toString();
                System.out.print(message);
            }
        }
    }

    private void printPrompt() {
        String state = client.getState().toString();
        System.out.print(SET_TEXT_COLOR_DARK_GREY + "\n[" + state + "] >>> " + SET_TEXT_COLOR_RED);
    }
}
