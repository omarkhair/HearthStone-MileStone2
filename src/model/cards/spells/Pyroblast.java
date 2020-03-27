package model.cards.spells;

import exceptions.InvalidTargetException;
import model.cards.Rarity;
import model.cards.minions.Minion;
import model.heroes.Hero;

public class Pyroblast extends Spell implements HeroTargetSpell, MinionTargetSpell {
	public Pyroblast()
	{
		super("Pyroblast", 10, Rarity.EPIC);
	}

	@Override
	//invalid target issue
	public void performAction(Minion m) throws InvalidTargetException {
		Minion.damage(m, 10);
		
	}

	@Override
	public void performAction(Hero h) {
		Hero.damage(h, 10);
		
	}

	@Override
	public Spell clone() throws CloneNotSupportedException {
		Pyroblast res=new Pyroblast();
		return res;
	}
	
}
