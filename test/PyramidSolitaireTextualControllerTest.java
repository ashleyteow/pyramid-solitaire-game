import static junit.framework.TestCase.assertEquals;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for a {@code PyramidSolitaireTextualController} object.
 */
public class PyramidSolitaireTextualControllerTest {
  private BasicPyramidSolitaire model;
  private Appendable out;

  @Before
  public void setup() {
    model = new BasicPyramidSolitaire();
    out = new StringBuilder();
  }

  // playGame Tests
  @Test (expected = IllegalArgumentException.class)
  public void controllerConstructorWhenReadableObjIsNull() {
    Readable in = null;
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
  }

  @Test (expected = IllegalArgumentException.class)
  public void controllerConstructorWhenAppendableObjIsNull() {
    Readable in = new StringReader("rm2 7 1 7 4\n");
    out = null;
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
  }

  @Test (expected = IllegalArgumentException.class)
  public void playGameWhenModelIsNull() {
    Readable in = new StringReader("rm2 7 1 7 4\n");
    model = null;
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, model.getDeck(), false, 7,3);
  }

  @Test (expected = IllegalStateException.class)
  public void catchIOExceptionTest() {
    Readable in = new StringReader("rm2 7 1 7 4\n");
    PyramidSolitaireController control = new PyramidSolitaireTextualController(
        in, new MockAppendable());
    control.playGame(model, model.getDeck(), false, 7,3);
  }

  @Test (expected = IllegalStateException.class)
  public void playGameWithIllegalStartGameArgs() {
    Readable in = new StringReader("rm2 7 1 7 4\n");
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, model.getDeck(), false, 10,3);
  }

  @Test
  public void playGamePrematureQuitTest() throws IOException {
    Readable in = new StringReader("rm2 7 1 7 4\nrm2 7 2 7 5\ndd q");
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, model.getDeck(), false, 7,3);
    assertEquals("            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "6♠  6♥  6♦  7♣  7♠  7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 112\n"
        + "            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "    6♥  6♦      7♠  7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 99\n"
        + "            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "        6♦          7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 86\n"
        + "\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "        6♦          7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 86\n", out.toString());
  }

  @Test
  public void playGameValidTest() throws IOException {
    Readable in = new StringReader("rm2 3 1 3 3\ndd 2\ndd 3\ndd 2\ndd 4\n"
        + "rmwd 2 3 2\nrmwd 1 2 2\nrmwd 1 2 1\nrmwd 3 1 1\n");
    Random rand = new Random(123);
    List<Card> testDeck = new ArrayList<Card>(model.getDeck());
    Collections.shuffle(testDeck, rand);
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, testDeck, false, 3,4);
    assertEquals("    9♦\n"
        + "  10♠ 8♦\n"
        + "2♠  5♣  J♦\n"
        + "Draw: 5♦, J♥, Q♣, A♦\n"
        + "Score: 45\n"
        + "    9♦\n"
        + "  10♠ 8♦\n"
        + "    5♣      \n"
        + "Draw: 5♦, J♥, Q♣, A♦\n"
        + "Score: 32\n"
        + "    9♦\n"
        + "  10♠ 8♦\n"
        + "    5♣      \n"
        + "Draw: 5♦, 9♠, Q♣, A♦\n"
        + "Score: 32\n"
        + "    9♦\n"
        + "  10♠ 8♦\n"
        + "    5♣      \n"
        + "Draw: 5♦, 9♠, 4♣, A♦\n"
        + "Score: 32\n"
        + "    9♦\n"
        + "  10♠ 8♦\n"
        + "    5♣      \n"
        + "Draw: 5♦, 8♣, 4♣, A♦\n"
        + "Score: 32\n"
        + "    9♦\n"
        + "  10♠ 8♦\n"
        + "    5♣      \n"
        + "Draw: 5♦, 8♣, 4♣, 4♠\n"
        + "Score: 32\n"
        + "    9♦\n"
        + "  10♠ 8♦\n"
        + "------------            \n"
        + "Draw: 5♦, K♣, 4♣, 4♠\n"
        + "Score: 27\n"
        + "    9♦\n"
        + "  10♠     \n"
        + "------------            \n"
        + "Draw: 3♠, K♣, 4♣, 4♠\n"
        + "Score: 19\n"
        + "    9♦\n"
        + "  ------------        \n"
        + "------------            \n"
        + "Draw: 7♣, K♣, 4♣, 4♠\n"
        + "Score: 9\n"
        + "You win!", out.toString());
  }

  @Test
  public void testOutOfBoundsRowInputTest() {
    Readable in = new StringReader("rmwd 2 3 8\n");
    Random rand = new Random(123);
    List<Card> testDeck = new ArrayList<Card>(model.getDeck());
    Collections.shuffle(testDeck, rand);
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, testDeck, false, 3,4);
    assertEquals("    9♦\n"
        + "  10♠ 8♦\n"
        + "2♠  5♣  J♦\n"
        + "Draw: 5♦, J♥, Q♣, A♦\n"
        + "Score: 45\n"
        + "Invalid arguments for rmwd move.Please play again.\n"
        + "    9♦\n"
        + "  10♠ 8♦\n"
        + "2♠  5♣  J♦\n"
        + "Draw: 5♦, J♥, Q♣, A♦\n"
        + "Score: 45\n", out.toString());
  }

  @Test
  public void playGameWhenYouRunOutOfMoves() {
    Readable in = new StringReader("rmwd 2 3 2\n");
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, model.getDeck(), false, 5,3);
    System.out.println(out.toString());
  }

  @Test
  public void playGameBadInput() {
    Readable in = new StringReader("rm5 rm5 rm 5\n");
    Random rand = new Random(123);
    List<Card> testDeck = new ArrayList<Card>(model.getDeck());
    Collections.shuffle(testDeck, rand);
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, testDeck, false, 3,4);
    System.out.println(out.toString());
  }

  @Test
  public void playGameIncompleteArgsForRemove() {
    // testing that playGame only asks for the row number if the command
    // (rm1 or rm2) was processed correctly but the row number was not entered correctly
  }

  @Test
  public void playGameIncorrectCommand() {
    // testing that playGame only asks for the drawCard index if the command
    // (rmwd) was processed correctly but the drawCard index was not entered correctly\

  }

  @Test
  public void playGameIncompleteArgsForRemoveWithDraw() {
    // testing that playGame only asks for the drawCard index if the command
    // (rmwd) was processed correctly but the drawCard index was not entered correctly\
    Readable in = new StringReader("rmwd 1 7 4");
    Random rand = new Random(123);
    List<Card> testDeck = new ArrayList<Card>(model.getDeck());
    Collections.shuffle(testDeck, rand);
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, testDeck, false, 4,3);
  }

  @Test
  public void playGameIncompleteArgs2ForRemoveWithDraw() {
    // testing that playGame only asks for the drawCard index if the command
    // (rmwd) was processed correctly but the row or card index was not entered correctly

  }

  @Test
  public void playGameIncompleteArgsForDiscardDraw() {
    // testing that playGame only asks for the drawCard index if the command
    // (dd) was processed correctly but the drawCard index was not entered correctly
  }

  @Test
  public void playGameWithQuitFromMoveType() {
    // testing that playGame returns gameQuit response when 'q' or "Q" is the move type
    Readable in = new StringReader("q 7 1 7 4");
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, model.getDeck(), false, 7,3);
    assertEquals("            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "6♠  6♥  6♦  7♣  7♠  7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 112\n"
        + "\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "6♠  6♥  6♦  7♣  7♠  7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 112\n", out.toString());
  }

  @Test
  public void playGameWithQuitFromRowType() {
    // testing that playGame returns gameQuit response when 'q' or "Q" is the move type
    Readable in = new StringReader("rm2 q 1 7 4");
    PyramidSolitaireController control = new PyramidSolitaireTextualController(in, out);
    control.playGame(model, model.getDeck(), false, 7,3);
    assertEquals("            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "6♠  6♥  6♦  7♣  7♠  7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 112\n"
        + "\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "            A♣\n"
        + "          A♠  A♥\n"
        + "        A♦  2♣  2♠\n"
        + "      2♥  2♦  3♣  3♠\n"
        + "    3♥  3♦  4♣  4♠  4♥\n"
        + "  4♦  5♣  5♠  5♥  5♦  6♣\n"
        + "6♠  6♥  6♦  7♣  7♠  7♥  7♦\n"
        + "Draw: 8♣, 8♠, 8♥\n"
        + "Score: 112\n", out.toString());
  }

  @Test
  public void playGameWithBogusInputsButCompletedSuccessfully() {
    // testing playGame with bogus inputs but completed successfully
  }

}



