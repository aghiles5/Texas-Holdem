import static org.junit.Assert.*;
import org.junit.Test;

public class HandTest {
	
	@Test
	public void test_detRank_HighCard() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(1, 1));
		testHand.addCard(new Card(0, 6));
		testHand.addCard(new Card(2, 8));
		testHand.addCard(new Card(1, 5));
		testHand.addCard(new Card(3, 0));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a high card hand", 0, testRank);
	}
	
	@Test
	public void test_detRank_OnePair() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(3, 11));
		testHand.addCard(new Card(0, 8));
		testHand.addCard(new Card(3, 7));
		testHand.addCard(new Card(3, 5));
		testHand.addCard(new Card(2, 7));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a one pair hand", 1, testRank);
	}
	
	@Test
	public void test_detRank_TwoPairs() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(0, 9));
		testHand.addCard(new Card(2, 12));
		testHand.addCard(new Card(3, 9));
		testHand.addCard(new Card(2, 5));
		testHand.addCard(new Card(1, 12));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a two pairs hand", 2, testRank);
	}
	
	@Test
	public void test_detRank_ThreeOfAKind() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(1, 1));
		testHand.addCard(new Card(1, 7));
		testHand.addCard(new Card(2, 7));
		testHand.addCard(new Card(0, 7));
		testHand.addCard(new Card(3, 0));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a three of a kind hand", 3, testRank);
	}
	
	@Test
	public void test_detRank_FullHouse() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(1, 3));
		testHand.addCard(new Card(0, 3));
		testHand.addCard(new Card(3, 7));
		testHand.addCard(new Card(2, 3));
		testHand.addCard(new Card(1, 7));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a full house hand", 6, testRank);
	}
	
	@Test
	public void test_detRank_FourOfAKind() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(0, 7));
		testHand.addCard(new Card(2, 6));
		testHand.addCard(new Card(2, 7));
		testHand.addCard(new Card(3, 7));
		testHand.addCard(new Card(1, 7));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a full house hand", 7, testRank);
	}
	
	@Test
	public void test_detRank_Straight() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(3, 5));
		testHand.addCard(new Card(1, 4));
		testHand.addCard(new Card(3, 6));
		testHand.addCard(new Card(2, 8));
		testHand.addCard(new Card(2, 7));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a straight hand", 4, testRank);
	}
	
	@Test
	public void test_detRank_StraightHighest() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(2, 12));
		testHand.addCard(new Card(1, 10));
		testHand.addCard(new Card(3, 9));
		testHand.addCard(new Card(0, 8));
		testHand.addCard(new Card(3, 11));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a straight hand", 4, testRank);
	}
	
	@Test
	public void test_detRank_StraightLowest() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(3, 1));
		testHand.addCard(new Card(0, 3));
		testHand.addCard(new Card(2, 12));
		testHand.addCard(new Card(1, 0));
		testHand.addCard(new Card(3, 2));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a straight hand", 4, testRank);
	}
	
	@Test
	public void test_detRank_Flush() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(2, 0));
		testHand.addCard(new Card(2, 5));
		testHand.addCard(new Card(2, 8));
		testHand.addCard(new Card(2, 7));
		testHand.addCard(new Card(2, 12));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a flush hand", 5, testRank);
	}
	
	@Test
	public void test_detRank_StraightFlush() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(3, 3));
		testHand.addCard(new Card(3, 2));
		testHand.addCard(new Card(3, 6));
		testHand.addCard(new Card(3, 4));
		testHand.addCard(new Card(3, 5));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a straight flush hand", 8, testRank);
	}
	
	@Test
	public void test_detRank_RoyalFlush() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(1, 8));
		testHand.addCard(new Card(1, 9));
		testHand.addCard(new Card(1, 11));
		testHand.addCard(new Card(1, 10));
		testHand.addCard(new Card(1, 12));
		
		int testRank = testHand.getRank();
		
		assertEquals("The given cards should form a royal flush hand", 9, testRank);
	}
	
}