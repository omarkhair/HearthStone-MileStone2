package model.cards;

import exceptions.*;

public abstract class Card implements Cloneable{
	private String name;
	private int manaCost;
	private Rarity rarity;

	public Card(String name, int manaCost, Rarity rarity) {
		this.name = name;
		setManaCost(manaCost);
		this.rarity = rarity;
	}

	public int getManaCost() {
		return manaCost;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
		if (this.manaCost > 10)
			this.manaCost = 10;
		else if (this.manaCost < 0)
			this.manaCost = 0;
	}
	public  Card clone() throws CloneNotSupportedException{
		//try polymorphism after commenting this
		return (Card) super.clone();
	}
}
