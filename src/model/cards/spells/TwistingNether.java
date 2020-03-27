package model.cards.spells;

import java.util.ArrayList;

import model.cards.Rarity;
import model.cards.minions.Minion;

public class TwistingNether extends Spell implements AOESpell {

	public TwistingNether() {
		super("Twisting Nether", 8, Rarity.EPIC);

	}

	@Override
	public void performAction(ArrayList<Minion> oppField, ArrayList<Minion> curField) {
		oppField.clear();
		curField.clear();
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		TwistingNether res=new TwistingNether();
		return res;
	}

}
