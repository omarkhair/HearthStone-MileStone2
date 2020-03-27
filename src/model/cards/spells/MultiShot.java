package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class MultiShot extends Spell implements AOESpell{

	public MultiShot() {
		super("Multi-Shot", 4,Rarity.BASIC);
		
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		if(oppField.size()==1) {
			Minion.damage(oppField.get(0),3);
		}
		else if(oppField.size()>1) {
			int ind = (int) (Math.random() * oppField.size());
			Minion.damage(oppField.get(ind),3);
			int ind2 = (int) (Math.random() * oppField.size());
			while(ind==ind2) {
				ind2 = (int) (Math.random() * oppField.size());
			}
			Minion.damage(oppField.get(ind2),3);
		}
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		MultiShot res=new MultiShot();
		return res;
	}

}
