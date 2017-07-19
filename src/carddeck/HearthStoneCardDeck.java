package carddeck;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import card.HearthStoneCard;

public class HearthStoneCardDeck implements CardDeck<HearthStoneCard> {
	
	private static final int NUMBER_OF_CARDS = 30;

	private Stack<HearthStoneCard> deck;
	
	public HearthStoneCardDeck() {
		deck = new Stack<>();
		List<HearthStoneCard> cards = generateCards();
		cards = shuffle(cards);
		
		for(HearthStoneCard card : cards) {
			deck.push(card);
		}
	}
	
	@Override
	public List<HearthStoneCard> shuffle(List<HearthStoneCard> cards) {
		int num;
		HearthStoneCard temp;
		
		for(int i=0; i<NUMBER_OF_CARDS; i++) {
			num = (int)(Math.random() * NUMBER_OF_CARDS);
			temp = cards.get(i);
			cards.set(i, cards.get(num));
			cards.set(num, temp);
		}
		
		return cards;
	}
	
	@Override
	public HearthStoneCard getCard() {
		return deck.pop();
	}
	
	private List<HearthStoneCard> generateCards() {
		List<HearthStoneCard> cards = new ArrayList<>();
		int attack, hp, mana;
		
		for(int i=0; i<NUMBER_OF_CARDS; i++) {
			attack = (int)(Math.random() * (i / 10 + 3)) + 1;
			hp = (int)(Math.random() * (i / 10 + 3)) + 1;
			mana = i / 10 + 1;
			cards.add(new HearthStoneCard(attack, hp, mana));
		}
		
		return cards;
	}
	
}
