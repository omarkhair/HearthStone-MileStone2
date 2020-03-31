
package model.heroes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import exceptions.*;
import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.*;

public class Warlock extends Hero {

	public Warlock() throws IOException, CloneNotSupportedException {
		super("Gul'dan");
	}

	@Override
	public void buildDeck() throws IOException, CloneNotSupportedException {
		ArrayList<Minion> neutrals = getNeutralMinions(getAllNeutralMinions("neutral_minions.csv"), 13);
		getDeck().addAll(neutrals);
		for (int i = 0; i < 2; i++) {
			getDeck().add(new CurseOfWeakness());
			getDeck().add(new SiphonSoul());
			getDeck().add(new TwistingNether());
		}
		Minion wilfred = new Minion("Wilfred Fizzlebang", 6, Rarity.LEGENDARY, 4, 4, false, false, false);
		getDeck().add(wilfred);
		Collections.shuffle(getDeck());

	}

	public void useHeroPower() throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
			FullHandException, FullFieldException, CloneNotSupportedException {
		super.useHeroPower();
		Card c = this.drawCard();
		if (c instanceof Minion && hasWilfred_Fizzlebang()) {
			c.setManaCost(0);
		}
		damage(this, 2);
		//decrementMana(2);
	}

	

}
