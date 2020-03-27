package model.heroes;

import java.io.*;
import java.util.*;
import engine.*;
import exceptions.*;
import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.*;

public abstract class Hero implements MinionListener {
	private String name;
	private int currentHP;
	private boolean heroPowerUsed;
	private int totalManaCrystals;
	private int currentManaCrystals;
	private ArrayList<Card> deck;
	private ArrayList<Minion> field;
	private ArrayList<Card> hand;
	@SuppressWarnings("unused")
	private int fatigueDamage;
	private HeroListener listener;
	private ActionValidator validator;

	

	public Hero(String name) throws IOException {
		this.name = name;
		currentHP = 30;
		deck = new ArrayList<Card>();
		field = new ArrayList<Minion>();
		hand = new ArrayList<Card>();
		buildDeck();
		for (Card card : deck) {
			if (card instanceof Minion) {
				((Minion) card).setListener(this);
			}
		}
		fatigueDamage=1;
	}
	public void setValidator(ActionValidator validator) {
		this.validator = validator;
	}

	public HeroListener getListener() {
		return listener;
	}

	public void setListener(HeroListener listener) {
		this.listener = listener;
	}

	public abstract void buildDeck() throws IOException;

	public static final ArrayList<Minion> getAllNeutralMinions(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		ArrayList<Minion> minions = new ArrayList<Minion>();
		String current = br.readLine();
		while (current != null) {
			String[] line = current.split(",");
			Minion minion = null;
			String n = line[0];
			int m = Integer.parseInt(line[1]);
			Rarity r = null;
			switch ((line[2])) {
			case "b":
				r = Rarity.BASIC;
				break;
			case "c":
				r = Rarity.COMMON;
				break;
			case "r":
				r = Rarity.RARE;
				break;
			case "e":
				r = Rarity.EPIC;
				break;
			case "l":
				r = Rarity.LEGENDARY;
				break;
			}
			int a = Integer.parseInt(line[3]);
			int p = Integer.parseInt(line[4]);
			boolean t = line[5].equals("TRUE") ? true : false;
			boolean d = line[6].equals("TRUE") ? true : false;
			boolean c = line[7].equals("TRUE") ? true : false;
			if (!n.equals("Icehowl"))
				minion = new Minion(n, m, r, a, p, t, d, c);
			else
				minion = new Icehowl();
			minions.add(minion);
			current = br.readLine();
		}
		br.close();
		return minions;
	}

	public static final ArrayList<Minion> getNeutralMinions(ArrayList<Minion> minions, int count) {
		ArrayList<Minion> res = new ArrayList<Minion>();
		int i = 0;
		while (i < count) {

			int index = (int) (Math.random() * minions.size());
			Minion minion = minions.get(index);
			int occ = 0;
			for (int j = 0; j < res.size(); j++) {
				if (res.get(j).getName().equals(minion.getName()))
					occ++;
			}
			try {
				if (occ == 0) {
					res.add(minion.clone());
					i++;
				} else if (occ == 1 && minion.getRarity() != Rarity.LEGENDARY) {
					res.add(minion.clone());
					i++;
				}
			} catch (CloneNotSupportedException e) {

			}

		}
		return res;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int hp) {
		this.currentHP = hp;
		if (this.currentHP > 30)
			this.currentHP = 30;
		else if (this.currentHP <= 0) {
			this.currentHP = 0;
			// how to know which one?
			listener.onHeroDeath();
		}
	}

	public int getTotalManaCrystals() {
		return totalManaCrystals;
	}

	public void setTotalManaCrystals(int totalManaCrystals) {
		this.totalManaCrystals = totalManaCrystals;
		if (this.totalManaCrystals > 10)
			this.totalManaCrystals = 10;
	}

	public int getCurrentManaCrystals() {
		return currentManaCrystals;
	}

	public void setCurrentManaCrystals(int currentManaCrystals) {
		this.currentManaCrystals = currentManaCrystals;
		if (this.currentManaCrystals > 10)
			this.currentManaCrystals = 10;
	}

	public ArrayList<Minion> getField() {
		return field;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public boolean isHeroPowerUsed() {
		return heroPowerUsed;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setHeroPowerUsed(boolean powerUsed) {
		this.heroPowerUsed = powerUsed;
	}

	public String getName() {
		return name;
	}

	public void onMinionDeath(Minion m) {
		field.remove(m);
	}

	public boolean hasTauntInField() {
		for (Minion minion : field) {
			if (minion.isTaunt())
				return true;
		}
		return false;
	}

	public void useHeroPower() throws NotEnoughManaException, HeroPowerAlreadyUsedException, NotYourTurnException,
			FullHandException, FullFieldException, CloneNotSupportedException {
		//decrement mana cost leh ya3am
		validator.validateUsingHeroPower(this);
		validator.validateTurn(this);

	}

	public void useHeroPower(Hero target) throws NotEnoughManaException, HeroPowerAlreadyUsedException,
			NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
//		useHeroPower();
//		validator.validateUsingHeroPower(this);
//		validator.validateTurn(this);
		// empty
	}

	public void useHeroPower(Minion target) throws NotEnoughManaException, HeroPowerAlreadyUsedException,
			NotYourTurnException, FullHandException, FullFieldException, CloneNotSupportedException {
//		useHeroPower();
//		validator.validateUsingHeroPower(this);
//		validator.validateTurn(this);
		// empty
	}

	public void playMinion(Minion m) throws NotYourTurnException, NotEnoughManaException, FullFieldException {
		validator.validateTurn(this);
		validator.validateManaCost(m);
		validator.validatePlayingMinion(m);
		this.field.add(m);
		this.hand.remove(m);
		int manaCost=m.getManaCost();
		decrementMana(manaCost);
	}
	public void attackWithMinion(Minion attacker, Minion target) throws CannotAttackException, NotYourTurnException,
	TauntBypassException,InvalidTargetException, NotSummonedException{
		validator.validateTurn(this);
		validator.validateAttack(attacker,target);
		attacker.attack(target);
	}
	public void attackWithMinion(Minion attacker, Hero target) throws CannotAttackException, NotYourTurnException,
	TauntBypassException,NotSummonedException, InvalidTargetException{
		validator.validateTurn(this);
		validator.validateAttack(attacker,target);
		attacker.attack(target);
	}
	public void castSpell(FieldSpell s) throws NotYourTurnException,NotEnoughManaException{
		this.SpellCaster((Spell)s);
		s.performAction(getField());
		
	}
	public void castSpell(AOESpell s, ArrayList<Minion >oppField) throws NotYourTurnException, NotEnoughManaException{
		this.SpellCaster((Spell)s);
		s.performAction(oppField, getField());
		
	}
	public void castSpell(MinionTargetSpell s, Minion m) throws NotYourTurnException,NotEnoughManaException,
		 InvalidTargetException{
		this.SpellCaster((Spell)s);
		s.performAction(m);
		
	}
	public void castSpell(HeroTargetSpell s, Hero h) throws NotYourTurnException,NotEnoughManaException{
		this.SpellCaster((Spell)s);
		s.performAction(h);
		
	}
	public void castSpell(LeechingSpell s, Minion m) throws NotYourTurnException,NotEnoughManaException{
		this.SpellCaster((Spell)s);
		int amount = s.performAction(m);
		int cur=this.getCurrentHP();
		this.setCurrentHP(cur+amount);
		
	}
	public void endTurn() throws FullHandException, CloneNotSupportedException{
		listener.endTurn();
	}
	public Card drawCard() throws FullHandException, CloneNotSupportedException {
		if(deck.isEmpty()) {
			deliverFatigue();
			return null;
		}else {
			Card c=deck.remove(0);
			
			if(hand.size()==10) {
				throw new FullHandException(c);
			}
			hasChromaggus(c);
			hand.add(c);
			return c;
		}
	}
	
	private void deliverFatigue() {
		damage(this,fatigueDamage++);
	}
	protected void decrementMana(int amount) {
		int currentMana=getCurrentManaCrystals();
		setCurrentManaCrystals(currentMana-amount);
	}
	public static void damage(Hero h,int amount) {
		int curHP=h.getCurrentHP();
		h.setCurrentHP(curHP-amount);
	}
	public void SpellCaster(Spell s) throws NotYourTurnException, NotEnoughManaException {
		validator.validateTurn(this);
		validator.validateManaCost(s);
		int manaCost=s.getManaCost();
		manaCost-=hasKalycgos(s);
		manaCost=Math.max(manaCost, 0);
		s.setManaCost(manaCost);
		decrementMana(manaCost);
		getHand().remove(s);
	}
	private void hasChromaggus(Card c) throws CloneNotSupportedException {
		ArrayList<Minion> f=this.field;
		for (Minion minion : f) {
			if(minion.getName().equals("Chromaggus")) {
				//should we throw full hand exception??
				if(this.hand.size()<10) {
					Card c2=c.clone();
					System.out.println(c2);
					if(hasWilfred_Fizzlebang()) {
						c2.setManaCost(0);
						
					}
					hand.add(c2);
				}
			}
		}
	}
	private int hasKalycgos(Spell s) {
		int amount=0;
		if(this instanceof Mage) {
			ArrayList<Minion> f=this.field;
			for (Minion minion : f) {
				if(minion.getName().equals("Kalycgos")) {
					amount+=4;
				}
			}
		}
		return amount;
	}
	public boolean hasWilfred_Fizzlebang() {
		if (this instanceof Warlock) {
			ArrayList<Minion> f = this.getField();
			for (Minion minion : f) {
				if (minion.getName().equals("Wilfred Fizzlebang")) {
					return true;
				}
			}
			
		}
		return false;
	}

}
