package engine;

import exceptions.CannotAttackException;
import exceptions.InvalidTargetException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.minions.*;
import model.cards.minions.Minion;
import model.heroes.*;

public class Game  implements ActionValidator,HeroListener{
	private Hero firstHero;
	private Hero secondHero;
	private Hero currentHero;
	private Hero opponent;
	
	public Game(Hero p1, Hero p2)
	{
		firstHero=p1;
		secondHero=p2;
		
		int coin = (int) (Math.random()*2);
		currentHero= coin==0?firstHero:secondHero;
		opponent= currentHero==firstHero?secondHero:firstHero;
		currentHero.setCurrentManaCrystals(1);
		currentHero.setTotalManaCrystals(1);
		
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public Hero getOpponent() {
		return opponent;
	}
	public void validateTurn(Hero user) throws NotYourTurnException{
		if(user!=currentHero) {
			throw new NotYourTurnException() ;
		}
	}
	public void validateAttack(Minion attacker,Minion target)
			throws CannotAttackException, NotSummonedException, TauntBypassException,InvalidTargetException
	{
		if(attacker.isSleeping()||attacker.isAttacked()||attacker.getAttack()==0) {
			throw new CannotAttackException();
		}
		if(!currentHero.getField().contains(attacker)) {
			throw new NotSummonedException();
		}
		if(opponent.hasTauntInField()) {
			if(!target.isTaunt()) {
				throw new TauntBypassException();
			}
		}
		if(currentHero.getField().contains(target)) {
			throw new InvalidTargetException();
		}
	}
	public void validateAttack(Minion attacker,Hero target) 
			throws CannotAttackException, NotSummonedException, TauntBypassException,InvalidTargetException
	{
		if(attacker.isSleeping()||attacker.isAttacked()||attacker.getAttack()==0) {
			throw new CannotAttackException();
		}
		if(!currentHero.getField().contains(attacker)) {
			throw new NotSummonedException();
		}
		if(opponent.hasTauntInField()) {
			throw new TauntBypassException();
		}
		if(attacker instanceof Icehowl) {
			throw new InvalidTargetException();
		}
		if(target.equals(currentHero)) {
			throw new InvalidTargetException();
		}	
	}
	
	

}
