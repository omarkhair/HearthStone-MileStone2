package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Rarity;
import model.cards.minions.Minion;

public class Polymorph extends Spell implements MinionTargetSpell {

	public Polymorph() {
		super("Polymorph", 4, Rarity.BASIC);
	}

	@Override
	//invalid target issue
	//special effect problem
	public void performAction(Minion m) throws InvalidTargetException {
//		m.setAttack(1);
//		m.setCurrentHP(1);
//		m.setMaxHP(1);
//		m.setName("Sheep");
//		m.setDivine(false);
//		m.setTaunt(false);
//		m.setSleeping(true);
//		m.setManaCost(1);
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		Polymorph res=new Polymorph();
		return res;
	}

}
