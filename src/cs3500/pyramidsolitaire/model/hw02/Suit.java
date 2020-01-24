package cs3500.pyramidsolitaire.model.hw02;

/**
 * Represents a suit in a card, whether it be a HEART, SPADE, CLUB, or DIAMOND.
 */
public enum Suit {
  CLUBS('♣'), SPADES('♠'), HEARTS('♥'), DIAMONDS('♦');
  private final char value;

  Suit(char value) {
    this.value = value;
  }

  /**
   * Prints out a string representation of this suit.
   * @return this suit's string representation.
   */
  @Override
  public String toString() {
    return Character.toString(this.value);
  }
}
