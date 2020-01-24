package cs3500.pyramidsolitaire.model.hw02;

import cs3500.pyramidsolitaire.model.hw04.AbstractPyramidSolitaireModelImpl;

/**
 * Represents an implementation of Pyramid Solitaire Model.
 */
public class BasicPyramidSolitaire extends AbstractPyramidSolitaireModelImpl {
  /**
   * Constructs a {@code BasicPyramidSolitaire} object with given parameters to initialize
   * the size of the pyramid and draw pile.
   * @param numRows number of rows in the pyramid
   * @param numDraw number of cards in the draw pile
   *
   */
  public BasicPyramidSolitaire(int numRows, int numDraw) throws IllegalArgumentException {
    super(numRows, numDraw);
  }

  public BasicPyramidSolitaire() {
    super();
  }
}


