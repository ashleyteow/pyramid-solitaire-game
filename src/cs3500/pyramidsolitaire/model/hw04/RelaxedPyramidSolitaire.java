package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.Card;
import java.util.ArrayList;

/**
 * Represents a Pyramid Solitaire game with more relaxed rules.
 */
public class RelaxedPyramidSolitaire extends AbstractPyramidSolitaireModelImpl {

  /**
   * Constructs a {@code RelaxedPyramidSolitaire} object with given parameters to initialize
   * the size of the pyramid and draw pile.
   * @param numRows number of rows in the pyramid
   * @param numDraw number of cards in the draw pile
   *
   */
  public RelaxedPyramidSolitaire(int numRows, int numDraw) {
    super(numRows, numDraw);
  }

  public RelaxedPyramidSolitaire() {
    super();
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    if (this.pyramid == null) {
      throw new IllegalStateException("Game has not started!");
    }
    if (this.score == 0) {
      return true;
    }

    boolean gameOver = false;
    ArrayList<Card> exposedCards = findExposedCards();
    ArrayList<Integer> exposedCardVals = findExposedCardVals(exposedCards);
    ArrayList<Card> specialExposedCards = findSpecialExposedCards();
    ArrayList<Integer> drawCards = findAllDrawCards();
    System.out.println("Exposed Cards: " + exposedCards);
    System.out.println("Special Exposed Cards: " + specialExposedCards);

    for (Card specialCard : specialExposedCards) {
      for (Card card : exposedCards) {
        int valueNeededToSumTo13 = 13 - card.getCardValue().getValue();
        if (exposedCardVals.contains(valueNeededToSumTo13)) {
          gameOver = false;
        }
        else if (specialCard.getCardValue().getValue() == valueNeededToSumTo13) {
          // find where this specialExposedCard is in the pyramid
          // check if the card to this specialExposedCard's left or right is in exposedCards
          // if it is, then this is a valid move and therefore the game is not over

          // ** handle special relaxed rule
          gameOver = validateSpecialMove(exposedCards, specialCard);
        }
        else if (drawCards.contains(valueNeededToSumTo13)) {
          gameOver = false;
        }
        else {
          gameOver = true;
          this.drawPile.clear();
        }
      }
    }

    return gameOver;
  }

  /**
   * Determines whether there are partially exposed cards that are playable,
   * meaning the game is not over.
   * @param exposedCardSet arraylist of exposed cards
   * @param specialCard special card to check if playable
   * @return true if there are no special moves to be made, false otherwise
   */
  private boolean validateSpecialMove(ArrayList<Card> exposedCardSet,
      Card specialCard) {
    boolean gameOver = true;

    for (Card exposedCard : exposedCardSet) {
      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < getRowWidth(i); j++) {
          Card card = this.pyramid[i].get(j);
          if (card != null) {
            if (card.equals(specialCard)) {
              int valueNeededToSum13 = 13 - specialCard.getCardValue().getValue();
              // check if the left or right card in front of this card is exposed
              Card leftCoveredCard = this.pyramid[i + 1].get(j);
              int leftVal =
                  leftCoveredCard == null ? 0 : leftCoveredCard.getCardValue().getValue();
              Card rightCoveredCard = this.pyramid[i + 1].get(j + 1);
              int rightVal =
                  rightCoveredCard == null ? 0 : rightCoveredCard.getCardValue().getValue();

              if (leftVal == valueNeededToSum13) {
                gameOver = false;
                break;
              }
              else if (rightVal == valueNeededToSum13) {
                gameOver = false;
                break;
              }
            }
          }
        }
      }
    }
    return gameOver;
  }

  /**
   * Gathers all cards that are only blocked by 1 other card and is eligible to be
   * exposed under new relaxed conditions.
   * @return list of all special exposed cards
   */
  private ArrayList<Card> findSpecialExposedCards() {
    ArrayList<Card> specialExposedCards = new ArrayList<>();
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < getRowWidth(i); j++) {
        if (i < numRows - 1) {
          Card thisCard = this.pyramid[i].get(j);
          Card leftCoveredCard = this.pyramid[i + 1].get(j);
          Card rightCoveredCard = this.pyramid[i + 1].get(j + 1);
          if (leftCoveredCard == null && rightCoveredCard != null) {
            if (validCardToRemove(i + 1,j + 1)) {
              specialExposedCards.add(thisCard);
            }
          }
          else if (leftCoveredCard != null && rightCoveredCard == null) {
            if (validCardToRemove(i + 1,j) && thisCard != null) {
              specialExposedCards.add(thisCard);
            }
          }
        }
      }
    }
    return specialExposedCards;
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
      else if (!validCardToRemove(row1, card1)) {
        // first card is being blocked by one card, either on its left or right
        // second card is not being blocked by any cards (aka this is the card on the bottom)
        Card leftCoveredCard = this.pyramid[row1 + 1].get(card1);
        Card rightCoveredCard = this.pyramid[row1 + 1].get(card1 + 1);

        if (leftCoveredCard != null && leftCoveredCard.equals(secondCard)
            || rightCoveredCard != null && rightCoveredCard.equals(secondCard)) {
          checkIfSum13(row1, card1,row2, card2);
        }
        else {
          throw new IllegalArgumentException("Cannot remove a card that isn't exposed!");
        }
      }
      else if (!validCardToRemove(row2, card2)) {
        // first card is not being blocked by any cards (aka this is the card on the bottom)
        // second card is being blocked by one card, either on its left or right
        Card leftCoveredCard = this.pyramid[row2 + 1].get(card2);
        Card rightCoveredCard = this.pyramid[row2 + 1].get(card2 + 1);

        if (leftCoveredCard != null && leftCoveredCard.equals(firstCard)
            || rightCoveredCard != null && rightCoveredCard.equals(firstCard)) {
          checkIfSum13(row1, card1,row2, card2);
        }
        else {
          throw new IllegalArgumentException("Cannot remove a card that isn't exposed!");
        }
      }
      else {
        throw new IllegalArgumentException("Cannot remove a card that isn't exposed!");
      }
    }
  }
}
