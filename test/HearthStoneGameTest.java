import org.junit.Test;

import game.HearthStoneGame;

public class HearthStoneGameTest {

	private HearthStoneGame game;
	
	@Test
	public void gameStartTest() {
		game = new HearthStoneGame();
		game.gameStart();
	}
}
