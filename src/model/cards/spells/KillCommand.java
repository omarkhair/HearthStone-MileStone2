package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class KillCommand extends Spell implements MinionTargetSpell, HeroTargetSpell {

	public KillCommand() {
		super("Kill Command", 3, Rarity.COMMON);
		
	}

	@Override
	public void performAction(Hero h) {
		Hero.damage(h,3);
		
	}

	@Override
	//Invalid target issue
	public void performAction(Minion m) throws InvalidTargetException {
		Minion.damage(m, 5);
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		KillCommand res=new KillCommand();
		return res;
	}
}
