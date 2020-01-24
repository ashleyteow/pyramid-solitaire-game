package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a controller for Pyramid Solitaire game that allows users to interact with game.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private final Readable rd;
  private final Appendable ap;
  private boolean quit = false;

  /**
   * Constructs a {@code PyramidSolitaireTextualController} object.
   * @param rd {@code Readable} object
   * @param ap {@code Appendable} object
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable and Appendable objects cannot be null!");
    }
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck, boolean shuffle,
      int numRows, int numDraw) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Invalid startGame parameters!");
    }
    PyramidSolitaireView view = new PyramidSolitaireTextualView(model, this.ap);
    Scanner scan = new Scanner(this.rd);

    while (!model.isGameOver() && !quit) {
      try {
        view.render();
        this.ap.append("\nScore: " + model.getScore() + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Appendable unable to transmit output!");
      }
      String command = "";
      if (scan.hasNext()) {
        command = scan.next();
      }
      else {
        break;
      }
      handleUserInput(model, view, scan,command);
    }
    scan.close();
    // Game has been quit / over
    if (model.isGameOver()) {
      try {
        view.render();
      } catch (IOException io) {
        throw new IllegalStateException("Appendable unable to transmit output!");
      }
    }
    if (quit) {
      try {
        this.ap.append("\nGame quit!\nState of game when quit:\n");
        view.render();
        this.ap.append("\nScore: " + model.getScore() + "\n");
      } catch (IOException io) {
        throw new IllegalStateException("Appendable unable to transmit output!");
      }
    }
  }

  /**
   * Parses user input to interact with game.
   * @param model game model
   * @param view game view
   * @param scanner scanner to retrieven user input
   * @param command user command
   * @throws IllegalStateException is the appendable object of this model
   * is unable to transmit output
   */
  private void handleUserInput(PyramidSolitaireModel model,
      PyramidSolitaireView view, Scanner scanner, String command) throws IllegalStateException {
    ArrayList<Integer> coordinatesForCommand;
    ArrayList<Integer> methodArgs = new ArrayList<Integer>();
    int numArgs;
    String line = scanner.nextLine();
    Scanner scanLine = new Scanner(line);

    switch (command) {
      case "rm1":
        numArgs = 2;
        while (scanLine.hasNext()) {
          methodArgs.add(getNextIntArg(scanLine));
        }
        if (methodArgs.size() > numArgs || methodArgs.contains(-1)) {
          break;
        }
        try {
          model.remove(methodArgs.get(0), methodArgs.get(1));
        } catch (IllegalArgumentException e) {
          try {
            this.ap.append("Invalid arguments for rm1 move.");
          } catch (IOException io) {
            throw new IllegalStateException("Appendable unable to transmit output!");
          }
        }
        break;
      case "rm2":
        numArgs = 4;
        while (scanLine.hasNext()) {
          methodArgs.add(getNextIntArg(scanLine));
        }
        if (methodArgs.size() > numArgs || methodArgs.contains(-1)) {
          break;
        }
        try {
          model.remove(methodArgs.get(0), methodArgs.get(1), methodArgs.get(2), methodArgs.get(3));
        } catch (IllegalArgumentException e) {
          try {
            this.ap.append("Invalid arguments for rm2 move. Please play again.\n");
          } catch (IOException io) {
            throw new IllegalStateException("Appendable unable to transmit output!");
          }
        }
        break;
      case "rmwd":
        numArgs = 3;
        while (scanLine.hasNext()) {
          methodArgs.add(getNextIntArg(scanLine));
        }
        if (methodArgs.size() > numArgs || methodArgs.contains(-1)) {
          break;
        }
        try {
          model.removeUsingDraw(methodArgs.get(0), methodArgs.get(1),
              methodArgs.get(2));
        } catch (IllegalArgumentException e) {
          try {
            this.ap.append("Invalid arguments for rmwd move.Please play again.\n");
          } catch (IOException io) {
            throw new IllegalStateException("Appendable unable to transmit output!");
          }
        }
        break;
      case "dd":
        numArgs = 1;
        while (scanLine.hasNext()) {
          methodArgs.add(getNextIntArg(scanLine));
        }
        if (methodArgs.size() > numArgs || methodArgs.contains(-1)) {
          break;
        }
        try {
          model.discardDraw(methodArgs.get(0));
        } catch (IllegalArgumentException e) {
          try {
            this.ap.append("Invalid arguments for dd move.Please play again.\n");
          } catch (IOException io) {
            throw new IllegalStateException("Appendable unable to transmit output!");
          }
        }
        break;
      case "q":
        this.quit = true;
        break;
      default:
        try {
          this.ap.append("Invalid command to make in this game!\n");
        } catch (IOException io) {
          throw new IllegalStateException("Appendable unable to transmit output!");
        }
    }
  }

  /**
   * Determines whether this input is 'q' or 'Q' or a number.
   * @param input string to validate
   * @return true if this input is a desire to quit or a number, false otherwise
   */
  private boolean validInput(String input) {
    return input.matches("[0-9]+") || input.equalsIgnoreCase("q");
  }

  /**
   * Determines what arguments to pass to model moves.
   * @param scanner readable object
   * @return -1 if user has opted to quit, any int > -1 otherwise
   */
  private int getNextIntArg(Scanner scanner) throws IllegalStateException {
    String strInput = scanner.next();
    if (validInput(strInput)) {
      if (strInput.equalsIgnoreCase("q")) {
        this.quit = true;
        return -1;
      }
      else if (strInput.matches("[0-9]+")) {
        return Integer.parseInt(strInput) - 1;
      }
    }
    return -1;
  }
}
