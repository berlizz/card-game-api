package player;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import card.Card;
import card.HearthStoneCard;
import carddeck.CardDeck;
import carddeck.HearthStoneCardDeck;

public class HearthStonePlayer implements Player {
	
	private static final int PLAYER_TOTAL_HP = 30;
	
	private int hp;
	private int mana;
	private List<HearthStoneCard> hand;
	private List<HearthStoneCard> minions;
	private CardDeck<HearthStoneCard> deck;
	
	public HearthStonePlayer() {
		this.hp = PLAYER_TOTAL_HP;
		this.mana = 0;
		this.hand = new LinkedList<>();
		this.minions = new LinkedList<>();
		this.deck = new HearthStoneCardDeck();
	}

	@Override
	public String toString() {
		return "HearthStonePlayer [hp=" + hp + ", mana=" + mana + ", hand=" + hand + ", minions=" + minions + ", deck="
				+ deck + "]";
	}

	@Override
	public void playCard() {
		
		while(true) {
			showHand();
			int index = selectCard();
			
			try {
				minions.add(hand.get(index));
				hand.remove(index);
				break;
			} catch (IndexOutOfBoundsException e) {
				System.out.println("카드 선택의 범위를 벗어남");
			}
		}
	}

	@Override
	public void drawCard() {
		hand.add(deck.getCard());
	}
	
	@Override
	public void endTurn() {
		
	}
	
	public void attackMinion(HearthStonePlayer opponent) {
		showMyMinions();
		showOpponentMinions(opponent);
		
		while(true) {
			try {
				int myMinionIndex = selectMyMinion();
				int opponentMinionIndex = selectOpponentMinionIndex();
				
				HearthStoneCard myMinion = this.minions.get(myMinionIndex);
				HearthStoneCard opponentMinion = opponent.getMinions().get(opponentMinionIndex);
				
				myMinion.setHp(myMinion.getHp() - opponentMinion.getAttack());
				opponentMinion.setHp(opponentMinion.getHp() - myMinion.getAttack());
				
				if(myMinion.getHp() < 1) {
					minions.remove(myMinion);
				}
				
				if(opponentMinion.getHp() < 1) {
					opponent.getMinions().remove(opponentMinion);
				}
				
				break;
			} catch (IndexOutOfBoundsException e) {
				System.out.println("하수인 선택의 범위를 벗어남");
			}
		}
		
	}
	
	public void attackHero(HearthStonePlayer opponent) {
		showMyMinions();
		
		while(true) {
			try {
				int myMinionIndex = selectMyMinion();
				HearthStoneCard myMinion = this.minions.get(myMinionIndex);
				
				opponent.setHp(opponent.getHp() - myMinion.getAttack());
				
				break;
			} catch (IndexOutOfBoundsException e) {
				System.out.println("하수인 선택의 범위를 벗어남");
			}
		}
	}
	
	private void showHand() {
		int i = 0;
		for (Card card : hand) {
			System.out.println(i++ + " " + card.toString());
		}
	}
	
	private void showMyMinions() {
		int i=0;
		System.out.println("나의 하수인들 =================================");
		for (Card card : minions) {
			System.out.println(i++ + " " + card.toString());
		}
		System.out.println("==========================================");
		
		System.out.println();
	}
	
	private void showOpponentMinions(HearthStonePlayer opponent) {
		int i = 0;
		System.out.println("상대 하수인들 =================================");
		for (Card card : opponent.getMinions()) {
			System.out.println(i++ + " " + card.toString());
		}
		System.out.println("==========================================");
		
		System.out.println();
	}
	
	private int selectCard() {
		Scanner scan = new Scanner(System.in);
		System.out.print("카드 선택  ");
		
		return scan.nextInt();
	}
	
	private int selectOpponentMinionIndex() {
		Scanner scan = new Scanner(System.in);
		System.out.print("공격할 상대의 하수인 선택  ");
		
		return scan.nextInt();
	}

	private int selectMyMinion() {
		Scanner scan = new Scanner(System.in);
		System.out.print("공격할 나의 하수인 선택  ");
		
		return scan.nextInt();
	}
	

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public List<HearthStoneCard> getHand() {
		return hand;
	}

	public void setHand(List<HearthStoneCard> hand) {
		this.hand = hand;
	}

	public List<HearthStoneCard> getMinions() {
		return minions;
	}

	public void setMinions(List<HearthStoneCard> minions) {
		this.minions = minions;
	}

	public CardDeck<HearthStoneCard> getDeck() {
		return deck;
	}

	public void setDeck(CardDeck<HearthStoneCard> deck) {
		this.deck = deck;
	}

}
