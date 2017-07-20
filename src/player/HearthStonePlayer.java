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
	
	private int hp;          // 플레이어 영웅의 체력
	private int mana;        // 플레이어의 남은 마나
	private int totalMana;   // 플레이어의 총 마나
	
	private List<HearthStoneCard> hand;      // 덱에서 카드를 뽑아 보유한 리스트
	private List<HearthStoneCard> minions;   // 내놓은 하수인들의 리스트
	private CardDeck<HearthStoneCard> deck;  // 카드 덱
	
	public HearthStonePlayer() {
		this.hp = PLAYER_TOTAL_HP;
		this.mana = 1;
		this.totalMana = 1;
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
	// 카드 한장을 내기
	public void playCard() {
		
		while(true) {
			showHand();
			int index = selectCard();
			
			try {
				if(hand.get(index).getMana() > this.mana) {  // 마나가 부족하면 카드를 낼 수 없음
					System.out.println("마나가 부족함\n");
					return;
				}
				
				this.mana -= hand.get(index).getMana();      // 카드를 내면 현재 마나를 줄이고, 하수인 리스트에 추가 및 보유카드에서 삭제
				minions.add(hand.get(index));
				hand.remove(index);
				
				break;
			} catch (IndexOutOfBoundsException e) {          // 입력 숫자가 리스트의 범위를 벗어난 경우
				System.out.println("카드 선택의 범위를 벗어남");
			}
		}
	}

	@Override
	// 카드 한장을 뽑기
	public void drawCard() {
		hand.add(deck.getCard());
	}
	
	@Override
	// 턴 종료          마나회복, 하수인 공격가능 설정, 카드뽑기 
	public void endTurn() {
		if(totalMana < 10) totalMana++;
		this.mana = this.totalMana;
		for (HearthStoneCard minion : this.minions) minion.setAttackAvailable(true);
		drawCard();
	}
	
	// 플레이어의 하수인으로 상대의 하수인 공격
	public void attackMinion(HearthStonePlayer opponent) {
		if(minions.size() == 0 || opponent.getMinions().size() == 0) {
			System.out.println("플레이어 또는 공격할 상대방의 하수인이 없음\n");
			return;
		}
		
		showMyMinions();
		showOpponentMinions(opponent);
		
		while(true) {
			try {  
				int myMinionIndex = selectMyMinion();
				HearthStoneCard myMinion = this.minions.get(myMinionIndex);
				if(!myMinion.isAttackAvailable()) {                                  // 이번턴에 낸 카드와 한번 공격한 하수인은 공격 불가 
					System.out.println("선택한 하수인은 공격할 수 없음");
					return;
				}
				
				int opponentMinionIndex = selectOpponentMinion();
				HearthStoneCard opponentMinion = opponent.getMinions().get(opponentMinionIndex);
				
				myMinion.setHp(myMinion.getHp() - opponentMinion.getAttack());       // 공격에 관여된 하수인들의 체력을 줄임
				opponentMinion.setHp(opponentMinion.getHp() - myMinion.getAttack());
				
				myMinion.setAttackAvailable(false);    // 공격한 하수인은 공격 불가로 변경
				if(myMinion.getHp() < 1) {             // 플레이어 또는 상대의 하수인 체력이 0이하가 될 시 하수인 리스트에서 삭제
					minions.remove(myMinion);
				}
				
				if(opponentMinion.getHp() < 1) {
					opponent.getMinions().remove(opponentMinion);
				}
				
				break;
			} catch (IndexOutOfBoundsException e) {          // 입력 숫자가 리스트의 범위를 벗어난 경우
				System.out.println("하수인 선택의 범위를 벗어남");
			}
		}
		
	}
	
	// 플레이어의 하수인으로 상대영웅을 공격
	public void attackHero(HearthStonePlayer opponent) {
		if(minions.size() == 0) {
			System.out.println("공격할 하수인이 없음\n");
			return;
		}
		
		showMyMinions();
		
		while(true) {
			try {
				int myMinionIndex = selectMyMinion();
				HearthStoneCard myMinion = this.minions.get(myMinionIndex);
				if(!myMinion.isAttackAvailable()) {
					System.out.println("선택한 하수인은 공격할 수 없음");
					return;
				}
				
				opponent.setHp(opponent.getHp() - myMinion.getAttack());      // 상대 영웅의 체력을 줄임
				myMinion.setAttackAvailable(false);    // 공격한 하수인은 공격 불가로 변경
				
				break;
			} catch (IndexOutOfBoundsException e) {          // 입력 숫자가 리스트의 범위를 벗어난 경우
				System.out.println("하수인 선택의 범위를 벗어남");
			}
		}
	}
	
	// 보유한 카드 리스트 출력
	private void showHand() {
		int i = 0;
		for (Card card : hand) {
			System.out.println(i++ + " " + card.toString());
		}
	}
	
	// 플레이어 하수인 출력
	private void showMyMinions() {
		int i=0;
		StringBuilder sb = new StringBuilder();
		sb.append("나의 하수인들 ======================================================\n");
		
		for (HearthStoneCard hearthStoneCard : minions) {
			sb.append(i++ + " " + hearthStoneCard.toString() + "\n");
		}
		
		sb.append("===============================================================\n");
		System.out.println(sb.toString());
	}
	
	// 상대 하수인 출력
	private void showOpponentMinions(HearthStonePlayer opponent) {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("상대 하수인들 ======================================================\n");
		
		for (HearthStoneCard hearthStoneCard : opponent.getMinions()) {
			sb.append(i++ + " " + hearthStoneCard.toString() + "\n");
		}
		
		sb.append("===============================================================\n");
		System.out.println(sb.toString());
	}
	
	// 보유한 카드 리스트의 인덱스 입력
	private int selectCard() {
		Scanner scan = new Scanner(System.in);
		System.out.print("카드 선택  ");
		
		return scan.nextInt();
	}
	
	// 상대 하수인 리스트의 인덱스 입력
	private int selectOpponentMinion() {
		Scanner scan = new Scanner(System.in);
		System.out.print("공격할 상대의 하수인 선택  ");
		
		return scan.nextInt();
	}

	// 플레이어 하수인 리스트의 인덱스 입력
	private int selectMyMinion() {
		Scanner scan = new Scanner(System.in);
		System.out.print("공격할 나의 하수인 선택  ");
		
		return scan.nextInt();
	}
	

	// 이하 getter, setter
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

	public int getTotalMana() {
		return totalMana;
	}

	public void setTotalMana(int totalMana) {
		this.totalMana = totalMana;
	}

}
