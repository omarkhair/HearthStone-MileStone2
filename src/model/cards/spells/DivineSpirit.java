package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class DivineSpirit extends Spell implements MinionTargetSpell {

	public DivineSpirit() {
		super("Divine Spirit", 3, Rarity.BASIC);
		
	}

	@Override
	public void performAction(Minion m) throws InvalidTargetException {
		int curMax=m.getMaxHP();
		int cur=m.getCurrentHP();
		m.setMaxHP(2*curMax);
		m.setCurrentHP(2*cur);
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		DivineSpirit res=new DivineSpirit();
		return res;
	}

	

}
