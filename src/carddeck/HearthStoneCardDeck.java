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
	// 카드를 섞음 
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
	// 카드 한장 뽑기
	public HearthStoneCard getCard() {
		return deck.pop();
	}
	
	// 카드 생성
	private List<HearthStoneCard> generateCards() {
		List<HearthStoneCard> cards = new ArrayList<>();
		int attack, hp, mana;
		
		// 랜덤으로 attack, hp, mana를 생성하여 카드리스트에 저장
		for(int i=0; i<NUMBER_OF_CARDS; i++) {
			attack = (int)(Math.random() * (i / 10 + 3)) + 1;  // 1 ~ 5
			hp = (int)(Math.random() * (i / 10 + 3)) + 1;      // 1 ~ 5
			mana = i / 10 + 1;                                 // 1 ~ 3
			cards.add(new HearthStoneCard(attack, hp, mana));
		}
		
		return cards;
	}
	
}
