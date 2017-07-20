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
	
	private int hp;          // �÷��̾� ������ ü��
	private int mana;        // �÷��̾��� ���� ����
	private int totalMana;   // �÷��̾��� �� ����
	
	private List<HearthStoneCard> hand;      // ������ ī�带 �̾� ������ ����Ʈ
	private List<HearthStoneCard> minions;   // ������ �ϼ��ε��� ����Ʈ
	private CardDeck<HearthStoneCard> deck;  // ī�� ��
	
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
	// ī�� ������ ����
	public void playCard() {
		
		while(true) {
			showHand();
			int index = selectCard();
			
			try {
				if(hand.get(index).getMana() > this.mana) {  // ������ �����ϸ� ī�带 �� �� ����
					System.out.println("������ ������\n");
					return;
				}
				
				this.mana -= hand.get(index).getMana();      // ī�带 ���� ���� ������ ���̰�, �ϼ��� ����Ʈ�� �߰� �� ����ī�忡�� ����
				minions.add(hand.get(index));
				hand.remove(index);
				
				break;
			} catch (IndexOutOfBoundsException e) {          // �Է� ���ڰ� ����Ʈ�� ������ ��� ���
				System.out.println("ī�� ������ ������ ���");
			}
		}
	}

	@Override
	// ī�� ������ �̱�
	public void drawCard() {
		hand.add(deck.getCard());
	}
	
	@Override
	// �� ����          ����ȸ��, �ϼ��� ���ݰ��� ����, ī��̱� 
	public void endTurn() {
		if(totalMana < 10) totalMana++;
		this.mana = this.totalMana;
		for (HearthStoneCard minion : this.minions) minion.setAttackAvailable(true);
		drawCard();
	}
	
	// �÷��̾��� �ϼ������� ����� �ϼ��� ����
	public void attackMinion(HearthStonePlayer opponent) {
		if(minions.size() == 0 || opponent.getMinions().size() == 0) {
			System.out.println("�÷��̾� �Ǵ� ������ ������ �ϼ����� ����\n");
			return;
		}
		
		showMyMinions();
		showOpponentMinions(opponent);
		
		while(true) {
			try {  
				int myMinionIndex = selectMyMinion();
				HearthStoneCard myMinion = this.minions.get(myMinionIndex);
				if(!myMinion.isAttackAvailable()) {                                  // �̹��Ͽ� �� ī��� �ѹ� ������ �ϼ����� ���� �Ұ� 
					System.out.println("������ �ϼ����� ������ �� ����");
					return;
				}
				
				int opponentMinionIndex = selectOpponentMinion();
				HearthStoneCard opponentMinion = opponent.getMinions().get(opponentMinionIndex);
				
				myMinion.setHp(myMinion.getHp() - opponentMinion.getAttack());       // ���ݿ� ������ �ϼ��ε��� ü���� ����
				opponentMinion.setHp(opponentMinion.getHp() - myMinion.getAttack());
				
				myMinion.setAttackAvailable(false);    // ������ �ϼ����� ���� �Ұ��� ����
				if(myMinion.getHp() < 1) {             // �÷��̾� �Ǵ� ����� �ϼ��� ü���� 0���ϰ� �� �� �ϼ��� ����Ʈ���� ����
					minions.remove(myMinion);
				}
				
				if(opponentMinion.getHp() < 1) {
					opponent.getMinions().remove(opponentMinion);
				}
				
				break;
			} catch (IndexOutOfBoundsException e) {          // �Է� ���ڰ� ����Ʈ�� ������ ��� ���
				System.out.println("�ϼ��� ������ ������ ���");
			}
		}
		
	}
	
	// �÷��̾��� �ϼ������� ��뿵���� ����
	public void attackHero(HearthStonePlayer opponent) {
		if(minions.size() == 0) {
			System.out.println("������ �ϼ����� ����\n");
			return;
		}
		
		showMyMinions();
		
		while(true) {
			try {
				int myMinionIndex = selectMyMinion();
				HearthStoneCard myMinion = this.minions.get(myMinionIndex);
				if(!myMinion.isAttackAvailable()) {
					System.out.println("������ �ϼ����� ������ �� ����");
					return;
				}
				
				opponent.setHp(opponent.getHp() - myMinion.getAttack());      // ��� ������ ü���� ����
				myMinion.setAttackAvailable(false);    // ������ �ϼ����� ���� �Ұ��� ����
				
				break;
			} catch (IndexOutOfBoundsException e) {          // �Է� ���ڰ� ����Ʈ�� ������ ��� ���
				System.out.println("�ϼ��� ������ ������ ���");
			}
		}
	}
	
	// ������ ī�� ����Ʈ ���
	private void showHand() {
		int i = 0;
		for (Card card : hand) {
			System.out.println(i++ + " " + card.toString());
		}
	}
	
	// �÷��̾� �ϼ��� ���
	private void showMyMinions() {
		int i=0;
		StringBuilder sb = new StringBuilder();
		sb.append("���� �ϼ��ε� ======================================================\n");
		
		for (HearthStoneCard hearthStoneCard : minions) {
			sb.append(i++ + " " + hearthStoneCard.toString() + "\n");
		}
		
		sb.append("===============================================================\n");
		System.out.println(sb.toString());
	}
	
	// ��� �ϼ��� ���
	private void showOpponentMinions(HearthStonePlayer opponent) {
		int i = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("��� �ϼ��ε� ======================================================\n");
		
		for (HearthStoneCard hearthStoneCard : opponent.getMinions()) {
			sb.append(i++ + " " + hearthStoneCard.toString() + "\n");
		}
		
		sb.append("===============================================================\n");
		System.out.println(sb.toString());
	}
	
	// ������ ī�� ����Ʈ�� �ε��� �Է�
	private int selectCard() {
		Scanner scan = new Scanner(System.in);
		System.out.print("ī�� ����  ");
		
		return scan.nextInt();
	}
	
	// ��� �ϼ��� ����Ʈ�� �ε��� �Է�
	private int selectOpponentMinion() {
		Scanner scan = new Scanner(System.in);
		System.out.print("������ ����� �ϼ��� ����  ");
		
		return scan.nextInt();
	}

	// �÷��̾� �ϼ��� ����Ʈ�� �ε��� �Է�
	private int selectMyMinion() {
		Scanner scan = new Scanner(System.in);
		System.out.print("������ ���� �ϼ��� ����  ");
		
		return scan.nextInt();
	}
	

	// ���� getter, setter
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
