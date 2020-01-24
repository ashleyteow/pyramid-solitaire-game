import static org.junit.Assert.assertEquals;


import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for all public methods of RelaxedPyramidSolitaire.
 */
public class RelaxedPyramidSolitaireTest {
  private RelaxedPyramidSolitaire game1;
  private RelaxedPyramidSolitaire game2;

  @Before
  public void setup() {
    game1 = new RelaxedPyramidSolitaire();
    game2 = new RelaxedPyramidSolitaire(6,1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidNumRowsForConstructor2() {
    game1 = new RelaxedPyramidSolitaire(10, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidNumDrawForConstructor2() {
    game1 = new RelaxedPyramidSolitaire(4, 5);
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
  public void startGameWhenDeckHasDuplicates() {
    List<Card> exampleDeck = game1.getDeck();
    exampleDeck.add(new Card(CardValue.Ace, Suit.SPADES));
    this.game2.startGame(exampleDeck, false, 6,1);
  }

  // Start Game Tests
  @Test (expected = IllegalArgumentException.class)
  public void startGameWhenNumRowsAreZero() {
    this.game2.startGame(game2.getDeck(), false, 0,1);
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
    this.game1.startGame(game1.getDeck(), false, 7,2);
    assertEquals(112, this.game1.getScore());
  }

  @Test
  public void startGameTillYouWin() {
    Random rand = new Random(367);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game1.startGame(testDeck, false, 3, 2);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.removeUsingDraw(0,2,1);
    // removes 8 and 5 even though the 5 is not fully exposed
    game1.remove(2,0,1,0);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.removeUsingDraw(1,2,2);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.removeUsingDraw(1,1,1);
    game1.removeUsingDraw(0,0,0);
    assertEquals(true, game1.isGameOver());
  }

  @Test
  public void startGameValidTest2() {
    this.game2.startGame(game2.getDeck(), false, 4,1);
    assertEquals(18, this.game2.getScore());
  }

  // Remove Move with 4 Args Tests
  @Test (expected = IllegalStateException.class)
  public void removeWithFourWhenGameHasNotStarted() {
    this.game1.remove(6, 0, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenRow1IsNegative() {
    this.game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(-1, 0, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenRow2IsNegative() {
    this.game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(6, 0, -4,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenCard1IsNegative() {
    this.game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(6, -1, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenCard2IsNegative() {
    this.game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(6, 0, 6,-3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWithUnexposedCard() {
    Random rand = new Random(722943);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game1.startGame(testDeck, false, 3, 2);
    game1.removeUsingDraw(1,2,1);
    game1.removeUsingDraw(0,2,0);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.removeUsingDraw(0,1,0);
    game1.remove(2,2,0,0);
  }

  @Test
  public void removeWithFourUnexposedCardWithRelaxedRules2() {
    Random rand = new Random(722943);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game1.startGame(testDeck, false, 3, 2);
    game1.removeUsingDraw(1,2,1);
    game1.removeUsingDraw(0,2,0);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.removeUsingDraw(0,1,0);
    game1.remove(1,1,2,2);
  }

  @Test
  public void removeWithFourUnexposedCardWithRelaxedRules() {
    Random rand = new Random(367);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game1.startGame(testDeck, false, 3, 2);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.removeUsingDraw(0,2,1);
    assertEquals(37, this.game1.getScore());
    // removes 8 and 5 even though the 5 is not fully exposed
    game1.remove(2,0,1,0);
    assertEquals(24, this.game1.getScore());
  }

  // Remove Move with 2 Args Tests
  @Test (expected = IllegalStateException.class)
  public void removeWithTwoWhenGameHasNotStarted() {
    this.game1.remove(6, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithTwoWithInvalidCard() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(-1, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithTwoWithUnexposedCard() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game1.remove(0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithTwoWithCardsThatDontAddTo13() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(6, 0);
  }

  @Test
  public void removeWithTwoValidTest() {
    Random rand = new Random(9876588);
    List<Card> testDeck = new ArrayList<Card>(game2.getDeck());
    Collections.shuffle(testDeck, rand);
    game2.startGame(testDeck, false, 3, 2);
    assertEquals(41, this.game2.getScore());
    game2.remove(2,1);
    assertEquals(28, this.game2.getScore());
  }

  // Remove Move with Draw Cards Tests
  @Test (expected = IllegalStateException.class)
  public void removeWithDrawWhenGameNotStarted() {
    this.game1.removeUsingDraw(0, 6, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithDrawWithInvalidDrawCard() {
    game1.startGame(game1.getDeck(), true, 7, 3);
    this.game1.removeUsingDraw(9, 6, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithDrawWithUnexposedCard() {
    game1.startGame(game1.getDeck(), true, 7, 3);
    this.game1.removeUsingDraw(1, 0, 0);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithDrawWithCardsThatDontAddTo13() {
    Random rand = new Random(9876588);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game1.startGame(testDeck, false, 3, 2);
    game1.removeUsingDraw(1,2,0);
  }

  @Test
  public void removeWithDrawValidTest() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game1.remove(6,0,6,3);
    assertEquals(99, this.game1.getScore());
    game1.remove(6,1,6,6);
    assertEquals(86, this.game1.getScore());
    game1.remove(6,2,6,4);
    assertEquals(73, this.game1.getScore());
    game1.removeUsingDraw(0,5,1);
    assertEquals(68, this.game1.getScore());
    game1.removeUsingDraw(1,5,2);
    assertEquals(63, this.game1.getScore());
  }

  // Discard Draw Move Tests
  @Test (expected = IllegalStateException.class)
  public void discardDrawWhenGameHasNotStarted() {
    this.game2.discardDraw(2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void discardDrawWithNegativeDrawIndex() {
    this.game2.startGame(game2.getDeck(), false, 6, 1);
    this.game2.discardDraw(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void discardDrawWithInvalidDrawIndex() {
    this.game2.startGame(game2.getDeck(), false, 6, 1);
    this.game2.discardDraw(3);
  }

  @Test
  public void discardDrawValidTest() {
    game1.startGame(game1.getDeck(), true, 7, 3);
    int cardToDiscard = 0;
    Card oldDrawCard = (Card) game1.getDrawCards().get(cardToDiscard);
    game1.discardDraw(cardToDiscard);
    Card newDrawCard = (Card) game1.getDrawCards().get(cardToDiscard);
    assertEquals(false, oldDrawCard.equals(newDrawCard));
  }

  // isGameOver Tests
  @Test (expected = IllegalStateException.class)
  public void isGameOverWhenGameHasNotStarted() {
    this.game2.isGameOver();
  }

  @Test
  public void isGameOverTestWhenGameIsNotOver() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    assertEquals(false, this.game1.isGameOver());
  }
}