package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representing a deck of cards in a standard game of play.
 */
public class DeckOfCards {
  private List<Card> deck;


  /**
   * Constructor for a {@code DeckOfCards} instantiated with 52 cards for standard play.
   */
  public DeckOfCards() {
    this.deck = new ArrayList<>();
    for (CardValue cardVal : CardValue.values()) {
      for (Suit suit : Suit.values()) {
        Card card = new Card(cardVal, suit);
        this.deck.add(card);
      }
    }
  }

  /**
   * Constructor for a {@code DeckOfCards} instantiated with the given array of cards.
   * @param cards an array list of cards
   */
  public DeckOfCards(List cards) {
    this.deck = cards;
  }

  /**
   * Getter method for this deck of cards.
   * @return valid deck of cards.
   */
  public List<Card> getDeck() {
    return this.deck;
  }

  /**
   * Shuffles the deck in a random order.
   */
  public void shuffle() {
    Collections.shuffle(deck);
  }

  /**
   * Adds a card to the bottom of the deck.
   * @param card card to be added
   */
  public void addToDeck(Card card) {
    this.deck.add(card);
  }

}
