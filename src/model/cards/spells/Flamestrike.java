package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class Flamestrike extends Spell implements AOESpell {

	
	public Flamestrike()
	{
		super("Flamestrike",7,Rarity.BASIC);
	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		int size=oppField.size();
		for (int i = 0; i < oppField.size(); i++) {
			Minion m=oppField.get(i);
			Minion.damage(m, 4);
			if(oppField.size()!=size) {
				i--;
				size--;
			}
		}
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		Flamestrike res=new Flamestrike();
		return res;
	}
}
