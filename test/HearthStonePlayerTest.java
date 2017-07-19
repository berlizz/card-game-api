import java.util.List;

import org.junit.Before;
import org.junit.Test;

import card.Card;
import card.HearthStoneCard;
import player.HearthStonePlayer;

public class HearthStonePlayerTest {

	private HearthStonePlayer player;
	
	@Before
	public void initPlayer() {
		player = new HearthStonePlayer();
	}
	
	@Test
	public void ConstructPlayer() {
		System.out.println(player.toString());
	}
	
	@Test
	public void drawCardTest() {
		for(int i=0; i<30; i++) {
			player.drawCard();
		}
		
		List<HearthStoneCard> cards = player.getHand();
		for (Card card : cards) {
			System.out.println(card.toString());
		}
		System.out.println();
	}
	
	@Test
	public void playCardTest() {
		player.drawCard();
		player.drawCard();
		player.drawCard();
		
		player.playCard();
	}
	
	@Test
	public void attackMinionsTest() {
		// player.attackMinion(new HearthStonePlayer());
	}
	
}
