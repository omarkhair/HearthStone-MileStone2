package model.heroes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.*;
import model.cards.minions.Minion;
import model.cards.spells.DivineSpirit;
import model.cards.spells.HolyNova;
import model.cards.spells.ShadowWordDeath;

public class Priest extends Hero {

	public Priest() throws IOException, CloneNotSupportedException {
		super("Anduin Wrynn");
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> neutrals = getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 13);
		getDeck().addAll(neutrals);
		for (int i = 0; i < 2; i++) {
			getDeck().add(new DivineSpirit());
			getDeck().add(new HolyNova());
			getDeck().add(new ShadowWordDeath());
		}
		Minion velen = new Minion("Prophet Velen", 7, Rarity.LEGENDARY, 7, 7, false, false, false);

		getDeck().add(velen);
		Collections.shuffle(getDeck());

	}

	public void useHeroPower(Hero target) throws NotEnoughManaException, HeroPowerAlreadyUsedException,
			NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		int curHP = target.getCurrentHP();
		int amount;
		if (hasProphet_Velen())
			amount = 8;
		else
			amount = 2;
		target.setCurrentHP(curHP + amount);
		decrementMana(2);
	}

	

	public void useHeroPower(Minion target) throws NotEnoughManaException, HeroPowerAlreadyUsedException,
			NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		int curHP = target.getCurrentHP();
		int amount;
		if (hasProphet_Velen())
			amount = 8;
		else
			amount = 2;
		target.setCurrentHP(curHP + amount);
		//decrementMana(2);
	}
	private boolean hasProphet_Velen() {
		ArrayList<Minion> f = this.getField();
		for (Minion minion : f) {
			if (minion.getName().equals("Prophet Velen")) {
				return true;
			}
		}
		return false;
	}

}
