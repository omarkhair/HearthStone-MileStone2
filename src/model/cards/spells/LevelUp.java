package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class LevelUp extends Spell implements FieldSpell {

	public LevelUp() {
		super("Level Up!", 6, Rarity.EPIC);
		
	}

	@Override
	public void performAction(ArrayList<Minion> field) {
		for (Minion minion : field) {
			if(minion.getName().equals("Silver Hand Recruit")) {
				int cur=minion.getMaxHP();
				minion.setMaxHP(cur+1);
				cur=minion.getCurrentHP();
				minion.setCurrentHP(cur+1);
				cur=minion.getAttack();
				minion.setAttack(cur+1);
			}
		}
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		LevelUp res=new LevelUp();
		return res;
	}

}
