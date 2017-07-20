package game;
import java.util.Scanner;

import card.HearthStoneCard;
import player.HearthStonePlayer;

public class HearthStoneGame implements Game {
	
	private HearthStonePlayer player_1;
	private HearthStonePlayer player_2;
	
	private int player_1_score;          // �÷��̾� ������ ���� ü��
	private int player_2_score;
	
	private boolean gameOver;            // ���ھ 0���ϰ� �Ǹ� true
	
	private int command;                 // ����ڷκ��� �Է¹��� ���
	private static final int PLAY_CARD = 1;
	private static final int ATTACK_MINION = 2;
	private static final int ATTACK_HERO = 3;
	private static final int END_TURN = 4;
	
	private HearthStonePlayer turnPlayer;    // ����� �Է��� �÷��̾�
	private HearthStonePlayer opponent;      // ��� �÷��̾�
	
	public HearthStoneGame() {
		this.player_1 = new HearthStonePlayer();
		this.player_2 = new HearthStonePlayer();
		
		this.gameOver = false;
		
		this.command = 0;
	}
	

	@Override
	// ���� ����
	public void gameStart() {
		System.out.println("============================ �Ͻ����� ������ �����մϴ�  ============================");
		drawThreeCards(player_1, player_2);    // �� �÷��̾���� ī�带 3�� ����
		Scanner scan = new Scanner(System.in);
		
		turnPlayer = player_1;      // �÷��̾�1�� ����
		opponent = player_2;
		
		while(!gameOver) {
			showSituation();
			
			if(turnPlayer.equals(player_1)) {
				System.out.println("�÷��̾�1�� �����Դϴ�.");
			} else {
				System.out.println("�÷��̾�2�� �����Դϴ�.");
			}
			
			System.out.println("1.ī�峻��   2.�ϼ��� ����   3.���� ����   4.������");
			command = scan.nextInt();
			
			switch(command) {          // �Է¹��� ��ɿ� ���� �ش� �޼ҵ� ����
			case PLAY_CARD :
				playCard(turnPlayer);
				break;
			case ATTACK_MINION :
				attackMinion(turnPlayer, opponent);
				break;
			case ATTACK_HERO :
				attackHero(turnPlayer, opponent);
				break;
			case END_TURN :
				endTurn(turnPlayer);
				
				if(turnPlayer.equals(player_1)) {      // �÷��̾� �� ����
					turnPlayer = player_2;
					opponent = player_1;
					
					break;
				}
				
				turnPlayer = player_1;
				opponent = player_2;
				break;
			default :          // 1 ~ 4 �̿��� ���� �Է��� ���
				System.out.println("1 2 3 4 �� �ϳ� ����");
			}
			
			countScore();
			if(isGameOver()) {
				gameOver = true;
			}
		}
		
		if(player_1.getHp() < 1) {
			System.out.println("\n==============�÷��̾�2�� �¸�==============");
		}
		
		System.out.println("\n==============�÷��̾�1�� �¸�==============");
	}

	@Override
	// �÷��̾� ���� ü�� ������Ʈ
	public void countScore() {
		this.player_1_score = player_1.getHp();
		this.player_2_score = player_2.getHp();
	}
	
	// �÷��̾�� ����� �ϼ���, ü��, ���� ��Ȳ�� ���
	private void showSituation() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n\n\n\n��� ���� HP : " + opponent.getHp() + "\n");
		sb.append("��� �ϼ��ε� ======================================================\n");
		for (HearthStoneCard card : opponent.getMinions()) {
			sb.append(card.toString() + "\n");
		}
		sb.append("===============================================================\n\n");
		sb.append("���� �ϼ��ε� ======================================================\n");
		for (HearthStoneCard card : turnPlayer.getMinions()) {
			sb.append(card.toString() + "\n");
		}
		sb.append("===============================================================\n");
		sb.append("���� ���� HP : " + turnPlayer.getHp() + ", Mana : " + turnPlayer.getMana() + "/" + turnPlayer.getTotalMana() + "\n");
		System.out.println(sb.toString());
	}
	
	private void playCard(HearthStonePlayer player) {
		player.playCard();
	}
	
	private void attackMinion(HearthStonePlayer player, HearthStonePlayer opponent) {
		player.attackMinion(opponent);
	}
	
	private void attackHero(HearthStonePlayer player, HearthStonePlayer opponent) {
		player.attackHero(opponent);
	}
	
	private void drawThreeCards(HearthStonePlayer player_1, HearthStonePlayer player_2) {
		player_1.drawCard();
		player_1.drawCard();
		player_1.drawCard();
		player_2.drawCard();
		player_2.drawCard();
		player_2.drawCard();
	}
	
	private boolean isGameOver() {
		return (player_1_score < 1 || player_2_score < 1);
	}
	
	private void endTurn(HearthStonePlayer turnPlayer) {
		turnPlayer.endTurn();
	}

}
