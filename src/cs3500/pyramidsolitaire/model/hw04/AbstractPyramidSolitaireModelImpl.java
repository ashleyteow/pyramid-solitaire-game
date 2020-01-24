package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.DeckOfCards;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Abstract implementation of the Pyramid Solitaire game.
 */
public class AbstractPyramidSolitaireModelImpl implements PyramidSolitaireModel {
  public DeckOfCards deckOfCards;
  protected int numDraw;
  protected int numRows;
  protected int score;
  public ArrayList<Card>[] pyramid;
  protected ArrayList<Card> drawPile;

  /**
   * Constructs an {@code AbstractPyramidSolitaireModelImpl} object.
   * @param numRows number of rows in the pyramid
   * @param numDraw number of draw cards to be displayed
   * @throws IllegalArgumentException if arguments are invalid.
   */
  public AbstractPyramidSolitaireModelImpl(int numRows, int numDraw)
      throws IllegalArgumentException {
    if (numRows > 9 || numRows < 1 || numDraw < 0 || numDraw > 4) {
      throw new IllegalArgumentException("Invalid row and draw arguments!");
    }
    this.numRows = numRows;
    this.numDraw = numDraw;
    this.score = 0;
    this.drawPile = new ArrayList<Card>(numDraw);
  }

  public AbstractPyramidSolitaireModelImpl() {
    // Default constructor
  }

  @Override
  public List getDeck() {
    ArrayList<Card> deck = new ArrayList<Card>();
    for (CardValue cardVal : CardValue.values()) {
      for (Suit suit : Suit.values()) {
        Card card = new Card(cardVal, suit);
        deck.add(card);
      }
    }
    return deck;
  }

  @Override
  public void startGame(List deck, boolean shuffle, int numRows, int numDraw) {
    if (!checkValidDeck(deck)) {
      throw new IllegalArgumentException("Deck is invalid!");
    }
    else if (numRows < 1 || numRows > 9) {
      throw new IllegalArgumentException("Number of rows given is invalid!");
    }
    else if (numDraw < 1) {
      throw new IllegalArgumentException("Number of draw cards given is invalid!");
    }
    this.numRows = numRows;
    this.numDraw = numDraw;
    List<Card> copyOfDeck = new ArrayList<Card>(deck);
    if (shuffle) {
      Collections.shuffle(copyOfDeck);
    }
    buildPyramid(copyOfDeck);
    this.deckOfCards = new DeckOfCards(copyOfDeck);
  }

  /**
   * Initializes this {@code BasicPyramidSolitaire}'s pyramid representation of the game,
   * including mutating the draw pile.
   * @param deck the deck of cards to be used in the game
   * @return the 2D array representation of the pyramid.
   */
  protected ArrayList<Card> [] buildPyramid(List deck) {
    // build pyramid with given deck (shuffled/unshuffled) and update numDraw
    this.pyramid = new ArrayList[numRows];
    for (int i = 0; i < numRows; i++) {
      pyramid[i] = new ArrayList<Card>();
    }

    Iterator iterator = deck.iterator();
    int cardsPerRowCount = 1;
    for (int rowCounter = 0; rowCounter < numRows; rowCounter++) {
      for (int cardsInputted = 0; cardsInputted < cardsPerRowCount; cardsInputted++) {
        Card card = (Card) iterator.next();
        this.pyramid[rowCounter].add(card);
        this.score += card.getCardValue().getValue();
        iterator.remove();
      }
      cardsPerRowCount++;
    }
    this.drawPile = new ArrayList<Card>();
    for (int i = 0; i < numDraw; i++) {
      Card card = (Card) iterator.next();
      this.drawPile.add(card);
      iterator.remove();
    }
    return this.pyramid;
  }

  /**
   * Determines whether the given deck is a valid deck for this game.
   * @param deck deck of cards
   * @return true if this is a valid deck for this model game, false otherwise.
   * @throws IllegalArgumentException if the deck is null
   */
  protected boolean checkValidDeck(List<Card> deck) throws IllegalArgumentException {
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null!");
    }
    Set<Card> set = new HashSet<>(deck);
    return set.size() == deck.size();
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalStateException, IllegalArgumentException {
    // checking if the game has started
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (row1 < 0 || row1 > numRows || row2 < 0 || row2 > numRows || card1 < 0 || card1 > numRows
        || card2 < 0 || card2 > numRows) {
      throw new IllegalArgumentException("Coordinates are not in pyramid board!");
    }
    // handles cases where the player has selected two cards in the pyramid
    else {
      Card firstCard = this.pyramid[row1].get(card1);
      Card secondCard = this.pyramid[row2].get(card2);
      // checks whether the two cards the player has selected are exposed cards
      if (validCardToRemove(row1, card1) && validCardToRemove(row2, card2)) {
        // both cards are not blocked by any cards below them
        checkIfSum13(row1, card1, row2, card2);
      }
      else {
        throw new IllegalArgumentException("Cannot remove a card that isn't exposed!");
      }
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException, IllegalArgumentException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (row < 0 || row > numRows || card < 0 || card > getRowWidth(row)) {
      throw new IllegalArgumentException("Coordinates are not in pyramid board!");
    }
    else {
      if (validCardToRemove(row, card)) {
        int cardVal = this.pyramid[row].get(card).getCardValue().getValue();
        if (cardVal != 13) {
          throw new IllegalArgumentException("Cards do not add up to 13!");
        }
        else {
          this.pyramid[row].set(card, null);
          this.score -= cardVal;
        }
      }
      else {
        throw new IllegalArgumentException("Cannot remove a card that isn't exposed!");
      }
    }
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalStateException, IllegalArgumentException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (drawIndex < 0 || drawIndex > this.getNumDraw() - 1 || row < 0
        || row > numRows - 1 || card < 0 || card > getRowWidth(row)) {
      throw new IllegalArgumentException("Invalid indexes!");
    }
    else {
      if (validCardToRemove(row, card)) {
        int drawCardVal = this.drawPile.get(drawIndex).getCardValue().getValue();
        int cardVal = this.pyramid[row].get(card).getCardValue().getValue();
        if (drawCardVal + cardVal != 13) {
          throw new IllegalArgumentException("Cards do not add up to 13!");
        }
        else {
          Iterator iterator = this.deckOfCards.getDeck().iterator();
          Card newDrawCard = (Card) iterator.next();
          this.pyramid[row].set(card, null);
          this.score -= cardVal;
          this.drawPile.set(drawIndex, newDrawCard);
          iterator.remove();
        }
      }
      else {
        throw new IllegalArgumentException("Cannot remove a card that isn't exposed!");
      }
    }
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException, IllegalArgumentException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (drawIndex < 0 || drawIndex > numDraw - 1) {
      throw new IllegalArgumentException("Invalid index for drawIndex!");
    }
    if (this.deckOfCards.getDeck().size() == 0) {
      this.drawPile.remove(drawIndex);
    }
    else {
      Iterator iterator = this.deckOfCards.getDeck().iterator();
      Card card = (Card) iterator.next();
      this.drawPile.set(drawIndex, card);
      iterator.remove();
    }
  }

  @Override
  public int getNumRows() {
    if (this.pyramid == null) {
      return -1;
    }
    return this.numRows;
  }

  @Override
  public int getNumDraw() {
    if (this.pyramid == null) {
      return -1;
    }
    return this.numDraw;
  }

  @Override
  public int getRowWidth(int row) throws IllegalStateException, IllegalArgumentException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (row < 0 || row > numRows) {
      throw new IllegalArgumentException("Row is invalid!");
    }
    return row + 1;
  }

  /**
   * Updates the pyramid and score accordingly if the given card values sum to 13.
   * @param row1 row of first card
   * @param card1 column of first card
   * @param row2 row of second card
   * @param card2 column of second card
   * @throws IllegalArgumentException if the two cards do not sum to 13
   */
  protected void checkIfSum13(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException {
    Card firstCard = this.pyramid[row1].get(card1);
    Card secondCard = this.pyramid[row2].get(card2);
    int firstCardVal = firstCard.getCardValue().getValue();
    int secondCardVal = secondCard.getCardValue().getValue();

    if (firstCardVal + secondCardVal != 13) {
      throw new IllegalArgumentException("Cards do not add up to 13");
    }
    else {
      this.pyramid[row1].set(card1, null);
      this.score -= firstCardVal;
      this.pyramid[row2].set(card2, null);
      this.score -= secondCardVal;
    }
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (this.score == 0) {
      return true;
    }

    boolean gameOver = true;
    ArrayList<Card> exposedCards = findExposedCards();
    ArrayList<Integer> exposedCardVals = findExposedCardVals(exposedCards);
    ArrayList<Integer> drawCards = findAllDrawCards();

    for (Card card : exposedCards) {
      int cardVal = card.getCardValue().getValue();
      int valueNeededToSumTo13 = 13 - cardVal;
      if (exposedCardVals.contains(valueNeededToSumTo13)) {
        gameOver = false;
        break;
      }
      else if (drawCards.contains(valueNeededToSumTo13)) {
        gameOver = false;
        break;
      }
    }
    if (gameOver) {
      this.drawPile.clear();
    }
    return gameOver;
  }

  @Override
  public int getScore() throws IllegalStateException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    return this.score;
  }

  @Override
  public Object getCardAt(int row, int card) throws IllegalStateException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (row < 0 || row > numRows || card < 0 || card > getRowWidth(row)) {
      throw new IllegalArgumentException("Invalid card coordinates!");
    }
    return this.pyramid[row].get(card);
  }

  @Override
  public List getDrawCards() throws IllegalStateException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    return this.drawPile;
  }

  /**
   * Gathers all draw cards as well as cards left in the stock.
   * @return list of all draw/stock cards
   */
  protected ArrayList<Integer> findAllDrawCards() {
    ArrayList<Integer> drawCardVals = new ArrayList<Integer>();

    // Add draw cards
    for (Card c : this.drawPile) {
      drawCardVals.add(c.getCardValue().getValue());
    }
    Iterator iterator = this.deckOfCards.getDeck().iterator();
    while (iterator.hasNext()) {
      Card stockCard = (Card) iterator.next();
      drawCardVals.add(stockCard.getCardValue().getValue());
    }
    return drawCardVals;
  }

  /**
   * Determines whether this card is not being blocked by two cards in the row in front
   * of it.
   */
  protected boolean validCardToRemove(int row, int card) {
    Card aCard = this.pyramid[row].get(card);
    boolean valid = false;
    if (row == numRows - 1 && this.pyramid[row].get(card) != null) {
      valid = true;
    }
    else if (row < numRows - 1) {
      if (this.pyramid[row + 1].get(card) == null
          && this.pyramid[row + 1].get(card + 1) == null
          && this.pyramid[row].get(card) != null) {
        valid = true;
      }
    }

    return valid;
  }

  /**
   * Gathers values of exposed cards from the given array of exposed cards in the pyramid.
   * @param exposedCards array list of exposed cards in this pyramid
   * @return an arraylist of integers representing the values available/exposed
   */
  protected ArrayList<Integer> findExposedCardVals(ArrayList<Card> exposedCards) {
    ArrayList<Integer> exposedCardVals = new ArrayList<Integer>();
    for (Card c : exposedCards) {
      exposedCardVals.add(c.getCardValue().getValue());
    }
    return exposedCardVals;
  }

  /**
   * Gathers all exposed cards in the pyramid.
   * @return list of all playable cards
   */
  protected ArrayList<Card> findExposedCards() {
    ArrayList<Card> exposedCards = new ArrayList<Card>();

    for (int rowCounter = numRows - 1; rowCounter >= 0; rowCounter--) {
      for (int colCounter = 0; colCounter < getRowWidth(rowCounter); colCounter++) {
        if (this.pyramid[rowCounter].get(colCounter) != null) {
          Card card = this.pyramid[rowCounter].get(colCounter);
          if (rowCounter == numRows - 1) {
            exposedCards.add(card);
          }
          else if (rowCounter < numRows - 1 && this.pyramid[rowCounter + 1].get(colCounter) == null
              && this.pyramid[rowCounter + 1].get(colCounter + 1) == null) {
            exposedCards.add(card);
          }
        }
      }
    }
    return exposedCards;
  }
}
