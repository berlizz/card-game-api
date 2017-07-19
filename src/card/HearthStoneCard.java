package card;
public class HearthStoneCard implements Card {
	
	private int attack;
	private int hp;
	private int mana;
	
	public HearthStoneCard(int attack, int hp, int mana) {
		this.attack = attack;
		this.hp = hp;
		this.mana = mana;
	}

	@Override
	public String toString() {
		return "HearthStoneCard [attack=" + attack + ", hp=" + hp + ", mana=" + mana + "]";
	}

	@Override
	public int getHp() {
		return hp;
	}

	@Override
	public int getMana() {
		return mana;
	}
	
	@Override
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	@Override
	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
}
