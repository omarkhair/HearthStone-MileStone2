package model.cards.spells;

import java.util.ArrayList;

import model.cards.Card;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class CurseOfWeakness extends Spell implements AOESpell {

	public CurseOfWeakness() {
		super("Curse of Weakness", 2, Rarity.RARE);
		
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		for (Minion minion : oppField) {
			int cur=minion.getAttack();
			minion.setAttack(cur-2);
		}
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		CurseOfWeakness res=new CurseOfWeakness();
		return res;
	}

	

	
	
}
