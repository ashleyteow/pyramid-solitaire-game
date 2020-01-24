import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.DeckOfCards;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for a {@code PyramidSolitaireTextualView} object.
 */
public class PyramidSolitaireTextualViewTest {
  private BasicPyramidSolitaire game1;
  private PyramidSolitaireTextualView view;
  private DeckOfCards deck;

  @Before
  public void setup() {
    game1 = new BasicPyramidSolitaire();
    deck = new DeckOfCards();
  }

  @Test
  public void renderTest() throws IOException {
    game1.startGame(deck.getDeck(), false, 7,3);
    this.view = new PyramidSolitaireTextualView(game1);
    this.view.render();
  }

  @Test
  public void toStringWorks() {
    this.view = new PyramidSolitaireTextualView(game1);
    assertEquals("", view.toString());
    this.game1.startGame(deck.getDeck(), false, 7, 3);
    assertEquals(
        "            A♣\n"
            + "          A♠  A♥\n"
            + "        A♦  2♣  2♠\n"
            + "      2♥  2♦  3♣  3♠\n"
            + "    3♥  3♦  4♣  4♠  4♥\n"
            + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
            + "6♠  6♥  6♦  7♣  7♠  7♥  7♦\n"
            + "Draw: 8♣, 8♠, 8♥", view.toString());
    this.game1.remove(6,1,6,3);
    assertEquals(
        "            A♣\n"
            + "          A♠  A♥\n"
            + "        A♦  2♣  2♠\n"
            + "      2♥  2♦  3♣  3♠\n"
            + "    3♥  3♦  4♣  4♠  4♥\n"
            + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
            + "6♠      6♦      7♠  7♥  7♦\n"
            + "Draw: 8♣, 8♠, 8♥", view.toString());
    this.game1.remove(6,2,6,4);
    assertEquals(
        "            A♣\n"
            + "          A♠  A♥\n"
            + "        A♦  2♣  2♠\n"
            + "      2♥  2♦  3♣  3♠\n"
            + "    3♥  3♦  4♣  4♠  4♥\n"
            + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
            + "6♠                  7♥  7♦\n"
            + "Draw: 8♣, 8♠, 8♥", view.toString());
    this.game1.removeUsingDraw(0,5,1);
    assertEquals(
        "            A♣\n"
            + "          A♠  A♥\n"
            + "        A♦  2♣  2♠\n"
            + "      2♥  2♦  3♣  3♠\n"
            + "    3♥  3♦  4♣  4♠  4♥\n"
            + "  4♦      5♠  5♥  5♦  6♣\n"
            + "6♠                  7♥  7♦\n"
            + "Draw: 8♦, 8♠, 8♥", view.toString());
  }
}
