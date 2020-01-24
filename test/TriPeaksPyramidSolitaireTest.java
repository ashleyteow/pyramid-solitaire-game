import static org.junit.Assert.assertEquals;

import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw04.TriPeaksPyramidSolitaire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for all public methods of TriPeaksPyramidSolitaire.
 */
public class TriPeaksPyramidSolitaireTest {
  private TriPeaksPyramidSolitaire game1;
  private TriPeaksPyramidSolitaire game2;

  @Before
  public void setup() {
    game1 = new TriPeaksPyramidSolitaire(6,2);
    game2 = new TriPeaksPyramidSolitaire(7,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidNumRowsForConstructor2() {
    game2 = new TriPeaksPyramidSolitaire(10, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidNumDrawForConstructor2() {
    game2 = new TriPeaksPyramidSolitaire(4, 5);
  }

  // Deck Validity Tests
  @Test
  public void checkDeckShuffledTest() {
    this.game1.startGame(game1.getDeck(), true, 7, 3);
    this.game2.startGame(game2.getDeck(), false, 7,3);
    assertEquals(true, game1.getDeck().size() == game2.getDeck().size());
  }

  @Test (expected = IllegalArgumentException.class)
  public void startGameWhenDeckIsNull() {
    this.game1.startGame(null, true, 7,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void startGameWhenDeckHas3ofAKind() {
    List<Card> exampleDeck = game1.getDeck();
    exampleDeck.add(new Card(CardValue.Ace, Suit.SPADES));
    this.game2.startGame(exampleDeck, false, 6,1);
  }

  // Start Game Tests
  @Test (expected = IllegalArgumentException.class)
  public void startGameWhenNumRowsAreZero() {
    this.game2.startGame(game2.getDeck(),
        false, 0,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void startGameWhenNumRowsAreGreaterThanNine() {
    this.game1.startGame(game1.getDeck(), false, 10,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void startGameWhenNumDrawIsNegative() {
    this.game1.startGame(game1.getDeck(), false, 6,-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void startGameWhenNumDrawIsGreaterThanFour() {
    this.game1.startGame(game1.getDeck(), false, 7,5);
  }

  @Test
  public void startGameValidTest1() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7, 2);
    assertEquals(445, game2.getScore());
  }

  @Test
  public void startGameValidTest2() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 4, 2);
    assertEquals(170, game2.getScore());
  }

  // Remove Move with 4 Args Tests
  @Test (expected = IllegalStateException.class)
  public void removeWithFourWhenGameHasNotStarted() {
    this.game1.remove(6, 0, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenRow1IsNegative() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    this.game2.startGame(testDeck, false, 7, 3);
    this.game2.remove(-1, 0, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenRow2IsNegative() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    this.game2.startGame(testDeck, false, 7, 3);
    this.game2.remove(6, 0, -4,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenCard1IsNegative() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    this.game2.startGame(testDeck, false, 7, 3);
    this.game2.remove(6, -1, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenCard2IsNegative() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    this.game2.startGame(testDeck, false, 7, 3);
    this.game2.remove(6, 0, 6,-3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWithUnexposedCard() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    this.game2.startGame(testDeck, false, 7, 3);
    this.game2.remove(5, 1, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourUnexposedCardWithRegularRules() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    this.game2.startGame(testDeck, false, 7, 3);
    game2.remove(2,0,1,0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenCardsDontAddTo13() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    this.game2.startGame(testDeck, false, 7, 3);
    game2.remove(6,0,6,2);
  }

  @Test
  public void removeWithFourValidTest() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    game2.remove(6,0,6,1);
  }

  // Remove Move with 2 Args Tests
  @Test (expected = IllegalStateException.class)
  public void removeWithTwoWhenGameHasNotStarted() {
    this.game2.remove(6, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithTwoWithInvalidCard() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    this.game2.remove(-1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithTwoWithUnexposedCard() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    game2.remove(0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithTwoWithCardsThatDontAddTo13() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    game2.remove(6,0);
  }

  @Test
  public void removeWithTwoValidTest() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    game2.remove(6,9);
  }

  // Remove Move with Draw Cards Tests
  @Test (expected = IllegalStateException.class)
  public void removeWithDrawWhenGameNotStarted() {
    this.game2.removeUsingDraw(0, 6, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithDrawWithInvalidDrawCard() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    this.game2.removeUsingDraw(9, 6, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithDrawWithUnexposedCard() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    this.game2.removeUsingDraw(1, 0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithDrawWithCardsThatDontAddTo13() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    game2.removeUsingDraw(1,2,0);
  }

  @Test
  public void removeWithDrawValidTest() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    game2.removeUsingDraw(0,6,6);
  }

  // Discard Draw Move Tests
  @Test (expected = IllegalStateException.class)
  public void discardDrawWhenGameHasNotStarted() {
    this.game2.discardDraw(2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void discardDrawWithNegativeDrawIndex() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    this.game2.discardDraw(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void discardDrawWithInvalidDrawIndex() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    this.game2.discardDraw(3);
  }

  @Test
  public void discardDrawValidTest() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    game2.discardDraw(1);
  }

  // isGameOver Tests
  @Test (expected = IllegalStateException.class)
  public void isGameOverWhenGameHasNotStarted() {
    this.game2.isGameOver();
  }

  @Test
  public void getNumRows() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game2.startGame(game2.getDeck(), false, 6, 1);
    assertEquals(7, this.game1.getNumRows());
    assertEquals(6, this.game2.getNumRows());
  }

  @Test
  public void getNumDrawTest() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game2.startGame(game2.getDeck(), false, 6, 1);
    assertEquals(3, this.game1.getNumDraw());
    assertEquals(1, this.game2.getNumDraw());
  }

  @Test
  public void getRowWidth() {
    Random rand = new Random(4999);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 7,3);
    assertEquals(13, this.game2.getRowWidth(6));
    assertEquals(12, this.game2.getRowWidth(5));
    assertEquals(6, this.game2.getRowWidth(1));
    assertEquals(3, this.game2.getRowWidth(0));
  }

  @Test (expected = IllegalStateException.class)
  public void getScoreException() {
    assertEquals(0, this.game1.getScore());
  }

  @Test
  public void getDeckTest() {
    assertEquals(104, game1.getDeck().size());
  }

}