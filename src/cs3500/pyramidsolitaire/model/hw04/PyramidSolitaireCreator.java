package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Represents a factory class for all types of PyramidSolitaire games.
 */
public class PyramidSolitaireCreator {

  /**
   * Represents a game type, whether it be BASIC, RELAXED,
   * or TRIPEAKS.
   */
  public enum GameType {
    BASIC, RELAXED, TRIPEAKS;
  }

  /**
   * Starts a new game of Pyramid Solitaire depending on given enum GameType.
   * @param type enum GameType
   * @return new instance of a Pyramid Solitaire game
   */
  public static <GameType> PyramidSolitaireModel create(GameType type) {
    PyramidSolitaireModel model;
    if (type == PyramidSolitaireCreator.GameType.BASIC) {
      model = new BasicPyramidSolitaire();
    }
    else if (type == PyramidSolitaireCreator.GameType.RELAXED) {
      model = new RelaxedPyramidSolitaire();
    }
    else {
      model = new TriPeaksPyramidSolitaire();
    }
    return model;
  }
}