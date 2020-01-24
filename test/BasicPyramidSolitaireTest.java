import static org.junit.Assert.assertEquals;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.CardValue;
import cs3500.pyramidsolitaire.model.hw02.Suit;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for all public methods of BasicPyramidSolitaire.
 */
public class BasicPyramidSolitaireTest {
  private BasicPyramidSolitaire game1;
  private BasicPyramidSolitaire game2;
  private BasicPyramidSolitaire game3;

  @Before
  public void setup() {
    game1 = new BasicPyramidSolitaire();
    game2 = new BasicPyramidSolitaire(6,1);
    game3 = new BasicPyramidSolitaire(3,2);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidNumRowsForConstructor2() {
    game2 = new BasicPyramidSolitaire(10, 1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidNumDrawForConstructor2() {
    game2 = new BasicPyramidSolitaire(4, 5);
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
    this.game1.startGame(game1.getDeck(), false, 7,2);
    assertEquals(112, this.game1.getScore());
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
    this.game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(5, 1, 6,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourUnexposedCardWithRegularRules() {
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
  }

  @Test (expected = IllegalArgumentException.class)
  public void removeWithFourWhenCardsDontAddTo13() {
    this.game1.startGame(game1.getDeck(), false, 7, 3);
    this.game1.remove(6, 0, 6,1);
  }

  @Test
  public void removeWithFourValidTest() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game1.remove(6,0,6,3);
    assertEquals(99, this.game1.getScore());
    game1.remove(6,1,6,6);
    assertEquals(86, this.game1.getScore());
    game1.remove(6,2,6,4);
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(game1);
    System.out.println(view.toString());
    assertEquals(73, this.game1.getScore());
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
    List<Card> testDeck = new ArrayList<Card>(game3.getDeck());
    Collections.shuffle(testDeck, rand);
    game3.startGame(testDeck, false, 3, 2);
    assertEquals(41, this.game3.getScore());
    game3.remove(2,1);
    assertEquals(28, this.game3.getScore());
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
    List<Card> testDeck = new ArrayList<Card>(game3.getDeck());
    Collections.shuffle(testDeck, rand);
    game3.startGame(testDeck, false, 3, 2);
    game3.removeUsingDraw(1,2,0);
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
  public void isGameOverWhenGameReallyIsOver() {
    game1.startGame(game1.getDeck(), false, 3,4);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(3);
    game1.removeUsingDraw(2, 2,0);
    game1.removeUsingDraw(0, 2,1);
    game1.removeUsingDraw(1, 2,2);
    game1.removeUsingDraw(0, 1,0);
    game1.removeUsingDraw(2, 1,1);
    game1.removeUsingDraw(3, 0,0);
    assertEquals(true, game1.isGameOver());
  }

  @Test
  public void isGameOverTestWhenGameIsNotOver() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    assertEquals(false, this.game1.isGameOver());
  }

  @Test
  public void isGameOverTestWhenYouWinTheGame() {
    Random rand = new Random(123);
    List<Card> testDeck = new ArrayList<Card>(game3.getDeck());
    Collections.shuffle(testDeck, rand);
    game3.startGame(testDeck, false, 3, 2);
    game3.remove(2,0,2,2);
    game3.discardDraw(0);
    game3.discardDraw(1);
    game3.discardDraw(0);
    game3.discardDraw(1);
    game3.discardDraw(0);
    game3.discardDraw(1);
    game3.removeUsingDraw(0,2,1);
    game3.discardDraw(0);
    game3.discardDraw(1);
    game3.removeUsingDraw(0,1,0);
    game3.discardDraw(0);
    game3.discardDraw(1);
    game3.discardDraw(0);
    game3.discardDraw(1);
    game3.discardDraw(0);
    game3.discardDraw(0);
    game3.discardDraw(0);
    game3.discardDraw(0);
    game3.discardDraw(0);
    game3.removeUsingDraw(0,1,1);
    game3.removeUsingDraw(1,0,0);
    assertEquals(true, game3.isGameOver());
  }

  @Test
  public void isGameOverTestWhenYouRunOutOfMoves() {
    Random rand = new Random(12345);
    List<Card> testDeck = new ArrayList<Card>(game1.getDeck());
    Collections.shuffle(testDeck, rand);
    game1.startGame(testDeck, false, 7, 3);
    game1.removeUsingDraw(0,6,1);
    game1.remove(6,2,6,6);
    game1.discardDraw(0);
    game1.removeUsingDraw(0,5,1);
    game1.discardDraw(0);
    game1.removeUsingDraw(0,6,3);
    game1.discardDraw(2);
    game1.discardDraw(1);
    game1.remove(5,2);
    game1.remove(6,0,4,1);
    game1.discardDraw(0);
    game1.discardDraw(0);
    game1.removeUsingDraw(0,5,0);
    game1.discardDraw(1);
    game1.discardDraw(1);
    game1.removeUsingDraw(1,6,4);
    game1.removeUsingDraw(1,6,5);
    game1.removeUsingDraw(2,5,5);
    game1.remove(5,3,4,0);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(2);
    game1.discardDraw(0);
    game1.discardDraw(1);
    game1.discardDraw(0);
    assertEquals(true, game1.isGameOver());
    assertEquals(95, game1.getScore());
  }

  @Test
  public void getNumRows() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game2.startGame(game2.getDeck(), false, 6, 1);
    game3.startGame(game3.getDeck(), false, 3, 2);
    assertEquals(7, this.game1.getNumRows());
    assertEquals(6, this.game2.getNumRows());
    assertEquals(3, this.game3.getNumRows());
  }

  @Test
  public void getDeckTest() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    assertEquals(52, this.game1.getDeck().size());
  }

  @Test
  public void getNumDrawTest() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game2.startGame(game2.getDeck(), false, 6, 1);
    game3.startGame(game3.getDeck(), false, 3, 2);
    assertEquals(3, this.game1.getNumDraw());
    assertEquals(1, this.game2.getNumDraw());
    assertEquals(2, this.game3.getNumDraw());
  }

  @org.junit.Test
  public void getRowWidth() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    assertEquals(7, this.game1.getRowWidth(6));
    assertEquals(6, this.game1.getRowWidth(5));
    assertEquals(2, this.game1.getRowWidth(1));
  }

  @Test (expected = IllegalStateException.class)
  public void getScoreException() {
    assertEquals(0, this.game1.getScore());
  }

  @Test
  public void getScore() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    assertEquals(112, this.game1.getScore());
    game1.remove(6,0,6,3);
    assertEquals(99, this.game1.getScore());
  }

  @Test
  public void getCardAt() {
    Card card1 = new Card(CardValue.Ace, Suit.CLUBS);
    Card card2 = new Card(CardValue.Six, Suit.SPADES);
    Card card3 = new Card(CardValue.Three, Suit.SPADES);
    game1.startGame(game1.getDeck(), false, 7, 3);
    assertEquals(true, this.game1.getCardAt(0,0).equals(card1));
    assertEquals(true, this.game1.getCardAt(6,0).equals(card2));
    assertEquals(true, this.game1.getCardAt(3,3).equals(card3));
  }

  @Test (expected = IllegalStateException.class)
  public void getCardAtWhenGameHasNotStarted() {
    game1.getCardAt(0,0);
  }

  @Test (expected = IllegalStateException.class)
  public void getDrawCardsWhenGameHasNotStarted() {
    game1.getDrawCards();
  }

  @Test
  public void getDrawCards() {
    game1.startGame(game1.getDeck(), false, 7, 3);
    game2.startGame(game2.getDeck(), false, 6, 1);
    game3.startGame(game3.getDeck(), false, 3, 2);
    assertEquals(3, this.game1.getDrawCards().size());
    assertEquals(1, this.game2.getDrawCards().size());
    assertEquals(2, this.game3.getDrawCards().size());
  }

}