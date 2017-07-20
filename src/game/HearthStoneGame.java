package game;
import java.util.Scanner;

import card.HearthStoneCard;
import player.HearthStonePlayer;

public class HearthStoneGame implements Game {
	
	private HearthStonePlayer player_1;
	private HearthStonePlayer player_2;
	
	private int player_1_score;          // 플레이어 영웅의 남은 체력
	private int player_2_score;
	
	private boolean gameOver;            // 스코어가 0이하가 되면 true
	
	private int command;                 // 사용자로부터 입력받을 명령
	private static final int PLAY_CARD = 1;
	private static final int ATTACK_MINION = 2;
	private static final int ATTACK_HERO = 3;
	private static final int END_TURN = 4;
	
	private HearthStonePlayer turnPlayer;    // 명령을 입력할 플레이어
	private HearthStonePlayer opponent;      // 상대 플레이어
	
	public HearthStoneGame() {
		this.player_1 = new HearthStonePlayer();
		this.player_2 = new HearthStonePlayer();
		
		this.gameOver = false;
		
		this.command = 0;
	}
	

	@Override
	// 게임 시작
	public void gameStart() {
		System.out.println("============================ 하스스톤 게임을 시작합니다  ============================");
		drawThreeCards(player_1, player_2);    // 각 플레이어들은 카드를 3장 뽑음
		Scanner scan = new Scanner(System.in);
		
		turnPlayer = player_1;      // 플레이어1이 선공
		opponent = player_2;
		
		while(!gameOver) {
			showSituation();
			
			if(turnPlayer.equals(player_1)) {
				System.out.println("플레이어1의 차례입니다.");
			} else {
				System.out.println("플레이어2의 차례입니다.");
			}
			
			System.out.println("1.카드내기   2.하수인 공격   3.영웅 공격   4.턴종료");
			command = scan.nextInt();
			
			switch(command) {          // 입력받은 명령에 따라 해당 메소드 실행
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
				
				if(turnPlayer.equals(player_1)) {      // 플레이어 턴 변경
					turnPlayer = player_2;
					opponent = player_1;
					
					break;
				}
				
				turnPlayer = player_1;
				opponent = player_2;
				break;
			default :          // 1 ~ 4 이외의 값을 입력한 경우
				System.out.println("1 2 3 4 중 하나 선택");
			}
			
			countScore();
			if(isGameOver()) {
				gameOver = true;
			}
		}
		
		if(player_1.getHp() < 1) {
			System.out.println("\n==============플레이어2의 승리==============");
		}
		
		System.out.println("\n==============플레이어1의 승리==============");
	}

	@Override
	// 플레이어 영웅 체력 업데이트
	public void countScore() {
		this.player_1_score = player_1.getHp();
		this.player_2_score = player_2.getHp();
	}
	
	// 플레이어와 상대의 하수인, 체력, 마나 상황을 출력
	private void showSituation() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n\n\n\n상대 영웅 HP : " + opponent.getHp() + "\n");
		sb.append("상대 하수인들 ======================================================\n");
		for (HearthStoneCard card : opponent.getMinions()) {
			sb.append(card.toString() + "\n");
		}
		sb.append("===============================================================\n\n");
		sb.append("나의 하수인들 ======================================================\n");
		for (HearthStoneCard card : turnPlayer.getMinions()) {
			sb.append(card.toString() + "\n");
		}
		sb.append("===============================================================\n");
		sb.append("나의 영웅 HP : " + turnPlayer.getHp() + ", Mana : " + turnPlayer.getMana() + "/" + turnPlayer.getTotalMana() + "\n");
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
