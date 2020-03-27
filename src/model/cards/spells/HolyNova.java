package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class HolyNova extends Spell implements AOESpell {

	public HolyNova() {
		super("Holy Nova", 5, Rarity.BASIC);
	
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		int size=oppField.size();
		for (int i = 0; i < oppField.size(); i++) {
			Minion m=oppField.get(i);
			Minion.damage(m, 2);
			if(oppField.size()!=size) {
				i--;
				size--;
			}
		}
		for (Minion minion : curField) {
			Minion.heal(minion,2);
		}
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		HolyNova res=new HolyNova();
		return res;
	}

}
