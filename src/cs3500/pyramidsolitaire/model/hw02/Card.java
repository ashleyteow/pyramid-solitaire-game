package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a single card in a standard deck of cards.
 */
public class Card {
  private CardValue cardValue;
  private Suit suit;

  /**
   * Constructs a {@code Card} object with a CardValue and a Suit.
   * @param cardValue the numeric value of this card.
   * @param suit the kind of suit this card is.
   * @throws IllegalArgumentException if card
   */
  public Card(CardValue cardValue, Suit suit) {
    if (cardValue.getValue() < 1 || cardValue.getValue() > 13) {
      throw new IllegalArgumentException("Invalid card values and suits!");
    }
    this.cardValue = cardValue;
    this.suit = suit;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Card)) {
      return false;
    }
    Card card = (Card) other;
    card.hashCode();
    return card.suit == this.suit && card.cardValue == this.cardValue;
  }

  @Override
  public int hashCode() {
    int result = 19;
    result = 31 * result + ((this.suit == null) ? 0 : this.suit.hashCode());
    result = 31 * result + ((this.cardValue == null) ? 0 : this.cardValue.hashCode());
    return result;
  }

  /**
   * Getter method for this Card's CardValue.
   * @return this cards {@code CardValue} object.
   */
  public CardValue getCardValue() {
    return this.cardValue;
  }

  /**
   * Prints out a string representation of this card with its numeric String value and
   * followed by its suit.
   * @return string representation of this single card.
   */
  @Override
  public String toString() {
    return this.cardValue.toString() + this.suit.toString();
  }
}
