package players;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

import cards.Card;
import cards.Deck;
import cards.Hand;

/**
 * This is a test file for the Player class and its child classes, AI class and
 * Human class
 *
 * @author John Lowie
 * @version 04/11/19
 */

public class PlayerTest {

    @Test
    public void testHumanConstructor() {
        Human a = new Human("Jeff");
        assertEquals("Player name is suppose to be: Jeff", "Jeff", a.getName());
        assertEquals(0, a.getStack());

        a.setStack(100);
        assertEquals("Player stack is suppose to be: 100", 100, a.getStack());
        assertEquals("", a.getAction());

        Human b = new Human(a.getName(), 1000);
        assertEquals("Player name is suppose to be: Jeff", "Jeff", b.getName());
        assertEquals(1000, b.getStack());

        b.setStack(200);
        assertEquals("Player stack is suppose to be: 200", 200, b.getStack());
    }

    @Test
    public void testAIConstructor() {
        AI a = new AI("Foo", 200);
        assertEquals("Player name is suppose to be: Foo", "Foo", a.getName());
        assertEquals("Player stack is suppose to be: 200", 200, a.getStack());

        a.setName("Hello");
        a.setStack(300);
        assertEquals("Player name is suppose to be: Hello", "Hello", a.getName());
        assertEquals("Player stack is suppose to be: 300", 300, a.getStack());
    }

    @Test
    public void testAISettersGetters() {
        ArrayList<String> n = new ArrayList<String>();
        String[] j = new String[] { "AdventurousAlonzo", "ButcherBoone", "CleverClayton", "DickheadDallas", "EasyEarle","FrenchmanFrank", "GallantGary", "HeartyHenry", "IdiotIgnacio", "ProspectorPatrick", "MagnificentMick", "SpeedyGonzales" };

        for (int i = 0; i < j.length; i++) {
            n.add(j[i]);
        }

        AI.setBetIntervals(100000);
        assertEquals("Bet interval is suppose to be: 1000", 1000, AI.getBetInterval());

        AI.addCPUName();
        assertEquals("CPU names is suppose to be" + n, n, AI.getCPUName());

        AI a = new AI();
        assertNotEquals("Player name is suppose to be: ", "", a.getName());

        AI.clearCPUName();
        n.clear();
        assertEquals("CPU name is suppose to be cleared", n, AI.getCPUName());
    }

    @Test
    public void otherTests() {
        Human a = new Human("Not Harvey");
        ArrayList<String> e = new ArrayList<String>();
        a.setName("Harvey");
        assertEquals("Player name is suppose to be: Harvey", "Harvey", a.getName());
        assertEquals("Player stack is suppose to be: 0", 0, a.getStack());

        a.setAction("Fold");
        assertEquals("Player action is suppose to be: Fold", "Fold", a.getAction());

        a.setBet(250);
        assertEquals("Bet amount is suppose to be" + 250, 250, a.getBet());

        a.setHighBet(1000);
        assertEquals("Highest bet amount is suppose to be: " + 1000, 1000, a.getHighBet());

        assertEquals(e, a.getHole());

    }

    @Test
    public void someMoreTests() {
        Deck deck = new Deck();
        Human a = new Human("Tom");
        ArrayList<Card> middleCards = new ArrayList<Card>();

        assertEquals("Player should not have any cards", middleCards, a.getHole());

        a.setHole(deck.dealSingle());
        a.setHole(deck.dealSingle());

        assertNotEquals("Player hole is not suppose to be empty", null, a.getHole());

        a.emptyHole();
        assertEquals("Player hole should be empty", middleCards, a.getHole());

        a.setHole(deck.dealSingle());
        a.setHole(deck.dealSingle());

        middleCards = deck.dealCard(middleCards);

        a.setHand(middleCards);
        assertNotEquals("Player hand should not be empty", null, a.getHand());

        middleCards = deck.dealCard(middleCards);
        a.setHand(middleCards);
        assertNotEquals("Player hand should not be empty", null, a.getHand());

        middleCards = deck.dealCard(middleCards);
        a.setHand(middleCards);
        assertNotEquals("Player hand should not be empty", null, a.getHand());

        middleCards.clear();
        a.emptyHole();
        a.emptyHand();
        assertEquals("Player hand should be empty", 0, a.getHand().getCards().size());
        
    }
}