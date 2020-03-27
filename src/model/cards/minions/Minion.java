package model.cards.minions;



import exceptions.*;
import model.cards.*;
import model.heroes.*;

public class Minion extends Card implements Cloneable {
	private int attack;
	private int maxHP;
	private int currentHP;
	private boolean taunt;
	private boolean divine;
	private boolean sleeping;
	private boolean attacked;
	private MinionListener listener;

	public void setListener(MinionListener listener) {
		this.listener = listener;
	}

	public Minion(String name, int manaCost, Rarity rarity, int attack, int maxHP, boolean taunt, boolean divine,
			boolean charge) {
		super(name, manaCost, rarity);
		setAttack(attack);
		this.maxHP = maxHP;
		this.currentHP = maxHP;
		this.taunt = taunt;
		this.divine = divine;
		if (!charge)
			this.sleeping = true;
	}

	public boolean isTaunt() {
		return taunt;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHp) {
		this.maxHP = maxHp;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
		if (this.currentHP > maxHP)
			this.currentHP = maxHP;
		else if (this.currentHP <= 0) {
			this.currentHP = 0;
			listener.onMinionDeath(this);
		}
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
		if (this.attack <= 0)
			this.attack = 0;
	}

	public void setTaunt(boolean isTaunt) {
		this.taunt = isTaunt;
	}

	public void setDivine(boolean divine) {
		this.divine = divine;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}

	public boolean isSleeping() {
		return sleeping;
	}

	public void setSleeping(boolean sleeping) {
		this.sleeping = sleeping;
	}

	public boolean isDivine() {
		return divine;
	}
	//.......................................................................
	public void attack(Minion target) {
		int amount=this.getAttack();
		damage(target,amount);
		amount=target.getAttack();
		damage(this,amount);
		
	}
	public static void damage(Minion target,int amount) {
		if(!target.divine) {
			int curHP=target.getCurrentHP();
			target.setCurrentHP(curHP-amount);
		}
		else {
			target.divine=false;
		}
	}
	public static void heal(Minion target,int amount) {
		int cur=target.getCurrentHP();
		target.setCurrentHP(cur+amount);
	}
	public void attack(Hero target) throws InvalidTargetException{
		if( this.getName().equals("Icehowl")) {
			throw new InvalidTargetException();
		}
		else {
			int curHP=target.getCurrentHP();
			target.setCurrentHP(curHP-this.getAttack());
		}
	}
	public Minion clone() throws CloneNotSupportedException{
		Minion res=new Minion(getName(), getManaCost(), getRarity(), attack, maxHP, taunt, divine, !sleeping);
		return res;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
