import org.junit.Test;

import card.HearthStoneCard;
import carddeck.CardDeck;
import carddeck.HearthStoneCardDeck;

public class HearthStoneCardDeckTest {

	private CardDeck<HearthStoneCard> deck;
	
	@Test
	public void generateCard() {
		deck = new HearthStoneCardDeck();
		
		for(int i=0; i<30; i++) {
			System.out.println(deck.getCard().toString());
		}
	}
}
