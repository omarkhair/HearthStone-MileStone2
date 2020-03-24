package engine;
import java.util.*;
import exceptions.*;
import model.cards.*;
import model.cards.minions.*;
import model.cards.minions.Minion;
import model.heroes.*;

public class Game implements ActionValidator, HeroListener {
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;
	private GameListener listener;

	

	public Game(Hero p1, Hero p2) {
		firstHero = p1;
		secondHero = p2;

		int coin = (int) (Math.random() * 2);
		currentHero = coin == 0 ? firstHero : secondHero;
		opponent = currentHero == firstHero ? secondHero : firstHero;
		currentHero.setCurrentManaCrystals(1);
		currentHero.setTotalManaCrystals(1);

	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public Hero getOpponent() {
		return opponent;
	}
	public void setListener(GameListener listener) {
		this.listener = listener;
	}

	public void validateTurn(Hero user) throws NotYourTurnException {
		if (user != currentHero) {
			throw new NotYourTurnException();
		}
	}

	public void validateAttack(Minion attacker, Minion target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		if (attacker.isSleeping() || attacker.isAttacked() || attacker.getAttack() == 0) {
			throw new CannotAttackException();
		}
		if (!currentHero.getField().contains(attacker)) {
			throw new NotSummonedException();
		}
		if (opponent.hasTauntInField()) {
			if (!target.isTaunt()) {
				throw new TauntBypassException();
			}
		}
		if (currentHero.getField().contains(target)) {
			throw new InvalidTargetException();
		}
	}

	public void validateAttack(Minion attacker, Hero target)
			throws CannotAttackException, NotSummonedException, TauntBypassException, InvalidTargetException {
		if (attacker.isSleeping() || attacker.isAttacked() || attacker.getAttack() == 0) {
			throw new CannotAttackException();
		}
		if (!currentHero.getField().contains(attacker)) {
			throw new NotSummonedException();
		}
		if (opponent.hasTauntInField()) {
			throw new TauntBypassException();
		}
		if (attacker instanceof Icehowl) {
			throw new InvalidTargetException();
		}
		if (target.equals(currentHero)) {
			throw new InvalidTargetException();
		}
	}

	public void validateManaCost(Card card) throws NotEnoughManaException {
		if (card.getManaCost() > currentHero.getCurrentManaCrystals()) {
			throw new NotEnoughManaException();
		}
	}

	public void validatePlayingMinion(Minion minion) throws FullFieldException {
		if (currentHero.getField().size() >= 7) {
			throw new FullFieldException();
		}
	}

	public void validateUsingHeroPower(Hero hero) throws NotEnoughManaException, HeroPowerAlreadyUsedException {
		if (currentHero.getCurrentManaCrystals() < 2) {
			throw new NotEnoughManaException();
		}
		if (currentHero.isHeroPowerUsed()) {
			throw new HeroPowerAlreadyUsedException();
		}
	}
	//listener may not be instantiated
	public void onHeroDeath() {
		listener.onGameOver();
	}
	public void damageOpponent(int amount) {
		int opHP=opponent.getCurrentHP();
		opponent.setCurrentHP(opHP-amount);
		if(opponent.getCurrentHP()==0) {
			onHeroDeath();
		}
	}
	public void endTurn() throws FullHandException, CloneNotSupportedException{
		switchTurns();
		updateHeroMana(currentHero);
		currentHero.setHeroPowerUsed(false);
		resetMinions(currentHero);
		//should we handle now or report -------we will only report
		currentHero.drawCard();
		
	}
	private static void resetMinions(Hero h) {
		ArrayList<Minion> field=h.getField();
		for (Minion minion : field) {
			minion.setAttacked(false);
			minion.setSleeping(false);
		}
	}

	private static void updateHeroMana(Hero h) {
		int prevTotal=h.getTotalManaCrystals();
		h.setTotalManaCrystals(prevTotal+1);
		h.setCurrentManaCrystals(h.getTotalManaCrystals());
	}

	//beware of .equals and reference manipulation
	private void switchTurns() {
		if(currentHero.equals(firstHero)) {
			currentHero=secondHero;
			opponent=firstHero;
		}else {
			currentHero=firstHero;
			opponent=secondHero;
		}
		
	}

}
