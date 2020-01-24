package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;

/**
 * Represents an implementation of a PyramidSolitaireView.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private final PyramidSolitaireModel<?> model;
  private final Appendable out;

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
    this.out = new StringBuilder();
  }

  @Override
  public void render() throws IOException {
    try {
      this.out.append(this.toString());
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Prints out the pyramid game shape for play.
   * @return String representation of the pyramid board.
   */
  public String toString() {
    String result = "";
    // game has not started yet
    if (this.model.getNumRows() == -1) {
      return result;
    }
    if (this.model.isGameOver() && this.model.getScore() != 0) {
      result = "Game Over. Score: " + this.model.getScore();
      return result;
    }
    if (this.model.isGameOver() && this.model.getScore() == 0) {
      result = "You win!";
      return result;
    }
    int trailingSpaces = (this.model.getNumRows() - 1) * 2;
    for (int i = 0; i < this.model.getNumRows(); i++) {
      // this loop handles trailing spaces, left of pyramid
      for (int j = 0; j < trailingSpaces; j++) {
        result += " ";
      }

      boolean emptyRow = true;
      int rowWidth = this.model.getRowWidth(i);
      for (int m = 0; m < rowWidth; m++) {
        if (this.model.getCardAt(i,m) != null) {
          emptyRow = false;
          break;
        }
      }
      if (emptyRow) {
        for (int blank = 0; blank < this.model.getNumRows(); blank++) {
          result += "----";
        }
      }
      // this loop handles how to display a card, whether it be a Card or empty, with
      // appropriate spaces
      for (int k = 0; k <= this.model.getRowWidth(i) - 1; k++) {
        Card card = (Card) this.model.getCardAt(i,k);
        if (card == null) {
          result += "    ";
        }
        else {
          int cardVal = card.getCardValue().getValue();
          if (this.model.getRowWidth(0) == 3) {
            // tripeaks
            if (i == 0 && k < rowWidth - 1) {
              result += card.toString() + "         ";
            }
            else if ((i == 1 && k == 1) || (i == 1 &&  k == 3)) {
              result += card.toString() + "     ";
            }
            else if (i == 2 && k < rowWidth - 1) {
              result += card.toString() + "  ";
            }
            else if (k < this.model.getRowWidth(i) - 1) {
              if (cardVal == 10) {
                result += card.toString() + " ";
              }
              else {
                result += card.toString() + "  ";
              }
            }
            else {
              result += card.toString();
            }
          }
          else {
            // normal peaks
            if (cardVal == 10) {
              result += card.toString() + " ";
            }
            else if (cardVal < 10 || cardVal > 10) {
              result += card.toString() + "  ";
            }
            else {
              result += card.toString();
            }
          }
        }
      }
      result += "\n";
      trailingSpaces -= 2;
    }
    result += "Draw:";
    int drawPileCounter = 0;
    for (Object c : this.model.getDrawCards()) {
      if (drawPileCounter == this.model.getNumDraw() - 1) {
        result += " ";
        result += c.toString();
      }
      else {
        result += " ";
        result += c.toString();
        result += ",";
      }
      drawPileCounter++;
    }
    return result;
  }
}
