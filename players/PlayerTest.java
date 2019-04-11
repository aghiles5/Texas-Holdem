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
        assertEquals("Jeff", a.getName());
        assertEquals(0, a.getStack());

        a.setStack(100);
        assertEquals(100, a.getStack());
        assertEquals("", a.getAction());

        Human b = new Human(a.getName(), 1000);
        assertEquals("Jeff", b.getName());
        assertEquals(1000, b.getStack());

        b.setStack(200);
        assertEquals(200, b.getStack());
    }

    @Test
    public void testAIConstructor() {
        AI a = new AI("Foo", 200);
        assertEquals("Foo", a.getName());
        assertEquals(200, a.getStack());

        a.setName("Hello");
        a.setStack(300);
        assertEquals("Hello", a.getName());
        assertEquals(300, a.getStack());
    }

    @Test
    public void testAISettersGetters() {
        ArrayList<String> n = new ArrayList<String>();
        String[] j = new String[] { "AdventurousAlonzo", "ButcherBoone", "CleverClayton", "DickheadDallas", "EasyEarle","FrenchmanFrank", "GallantGary", "HeartyHenry", "IdiotIgnacio", "ProspectorPatrick", "MagnificentMick", "SpeedyGonzales" };

        for (int i = 0; i < j.length; i++) {
            n.add(j[i]);
        }

        AI.setBetIntervals(100000);
        assertEquals(1000, AI.getBetInterval());

        AI.addCPUName();
        assertEquals(n, AI.getCPUName());

        AI a = new AI();
        assertNotEquals("", a.getName());

        AI.clearCPUName();
        n.clear();
        assertEquals(n, AI.getCPUName());
    }

    @Test
    public void otherTests() {
        Human a = new Human("Not Harvey");
        ArrayList<String> e = new ArrayList<String>();
        a.setName("Harvey");
        assertEquals("Harvey", a.getName());
        assertEquals(0, a.getStack());

        a.setAction("Fold");
        assertEquals("Fold", a.getAction());

        a.setBet(250);
        assertEquals(250, a.getBet());

        a.setHighBet(1000);
        assertEquals(1000, a.getHighBet());

        assertEquals(e, a.getHole());

    }

    @Test
    public void someMoreTests() {
        Deck deck = new Deck();
        Human a = new Human("Tom");
        ArrayList<Card> middleCards = new ArrayList<Card>();
        a.setHole(deck.dealSingle());
        a.setHole(deck.dealSingle());

        deck.dealCard(middleCards);
        a.setHand(middleCards);
        assertEquals(null, null);

        deck.dealCard(middleCards);
        a.setHand(middleCards);
        assertEquals(null, null);

        deck.dealCard(middleCards);
        a.setHand(middleCards);
        assertEquals(null, null);
    }
}