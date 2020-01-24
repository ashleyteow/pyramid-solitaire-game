package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import java.io.StringReader;

/**
 * Represents the main class to run a game of Pyramid Solitaire.
 */
public class PyramidSolitaire {

  /**
   * Entry point to the Pyramid Solitaire program.
   * @param args user input
   */
  public static void main(String[] args) {
    PyramidSolitaireModel model;
    int numRows = 7;
    int numDraw = 3;

    switch (args[0]) {
      case "basic":
        model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.BASIC);
        break;
      case "relaxed":
        model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.RELAXED);
        break;
      case "tripeaks":
        model = PyramidSolitaireCreator.create(PyramidSolitaireCreator.GameType.TRIPEAKS);
        break;
      default:
        return;
    }

    if (args.length == 3) {
      numRows = Integer.parseInt(args[1]);
      numDraw = Integer.parseInt(args[2]);
    }
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader(""), new StringBuilder());
    controller.playGame(model, model.getDeck(), false, numRows, numDraw);
  }
}
