package cs3500.pyramidsolitaire.model.hw02;

/**
 * Representing a card value in its numeric form for a {@code Card} object.
 */
public enum CardValue {
  Ace(1), Two(2), Three(3), Four(4), Five(5), Six(6),
  Seven(7), Eight(8), Nine(9), Ten(10), Jack(11), Queen(12), King(13);
  private final int value;

  /**
   * Contructs a {@code CardValue} object.
   * @param value the numeric value of a card.
   */
  CardValue(int value) {
    this.value = value;
  }

  /**
   * Getter method to retrieve this CardValue's numeric value.
   * @return the card's numeric value.
   */
  public int getValue() {
    return this.value;
  }

  /**
   * Prints out this card's numeric value as a string and Jack, Queen, or a King if its
   * value is 11, 12, or 13, respectively.
   * @return string value of this card's value.
   */
  @Override
  public String toString() {
    if (this.value == 1) {
      return "A";
    }
    else if (this.value == 11) {
      return "J";
    }
    else if (this.value == 12) {
      return "Q";
    }
    else if (this.value == 13) {
      return "K";
    }
    else {
      return String.valueOf(this.value);
    }
  }
}
