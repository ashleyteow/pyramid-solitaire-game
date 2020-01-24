package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a Pyramid Solitaire game with 3 pyramids as instead of 1.
 */
public class TriPeaksPyramidSolitaire extends AbstractPyramidSolitaireModelImpl {

  /**
   * Constructs a {@code TriPeaksPyramidSolitaire} object with given parameters to initialize
   * the size of the pyramid and draw pile.
   * @param numRows number of rows in the pyramid
   * @param numDraw number of cards in the draw pile
   *
   */
  public TriPeaksPyramidSolitaire(int numRows, int numDraw) {
    super(numRows, numDraw);
  }

  public TriPeaksPyramidSolitaire() {
    super();
  }

  @Override
  public List getDeck() {
    ArrayList<Card> deck = new ArrayList<Card>();
    for (CardValue cardVal : CardValue.values()) {
      for (Suit suit : Suit.values()) {
        Card card1 = new Card(cardVal, suit);
        Card card2 = new Card(cardVal, suit);
        deck.add(card1);
        deck.add(card2);
      }
    }
    return deck;
  }

  @Override
  protected boolean checkValidDeck(List<Card> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null!");
    }
    int count;
    for (int i = 0; i < deck.size(); i++) {
      if (deck.get(i) == null) {
        return false;
      }
      count = 0;
      for (int j = 0; j < deck.size(); j++) {
        if (i != j && deck.get(i) != null && deck.get(i).equals(deck.get(j))) {
          count += 1;
        }
      }
      if (count != 1) {
        return false;
      }
    }
    return true;
  }

  @Override
  protected ArrayList<Card> [] buildPyramid(List deck) {
    // build pyramid with given deck (shuffled/unshuffled) and update numDraw
    this.pyramid = new ArrayList[numRows];
    for (int i = 0; i < numRows; i++) {
      pyramid[i] = new ArrayList<Card>();
    }

    Iterator iterator = deck.iterator();
    int cardsPerRow = 3;
    for (int rowCounter = 0; rowCounter < numRows; rowCounter++) {
      int cardsPerRowCount = 0;
      while (cardsPerRowCount < cardsPerRow) {
        Card card = (Card) iterator.next();
        this.pyramid[rowCounter].add(card);
        this.score += card.getCardValue().getValue();
        iterator.remove();
        cardsPerRowCount++;
      }
      if (rowCounter < numRows / 2 - 1) {
        cardsPerRow += 3;
      }
      else {
        cardsPerRow++;
      }
    }
    this.drawPile = new ArrayList<Card>();
    for (int i = 0; i < numDraw; i++) {
      Card card = (Card) iterator.next();
      this.drawPile.add(card);
      iterator.remove();
    }
    return this.pyramid;
  }

  @Override
  public int getRowWidth(int row) throws IllegalStateException, IllegalArgumentException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (row < 0 || row > numRows) {
      throw new IllegalArgumentException("Row is invalid!");
    }
    int[] numCardsPerRow = new int[numRows];
    int rowWidth = 3;
    for (int i = 0; i < numRows; i++) {
      if (i == 0) {
        numCardsPerRow[i] = rowWidth;
      }
      else if (i < numRows / 2) {
        rowWidth = i * 3 + 3;
        numCardsPerRow[i] = i * 3 + 3;
      }
      else {
        numCardsPerRow[i] = rowWidth + 1;
        rowWidth = numCardsPerRow[i];
      }
    }
    return numCardsPerRow[row];
  }
}
