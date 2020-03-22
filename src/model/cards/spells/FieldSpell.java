package model.cards.spells;
import java.util.*;
import model.cards.minions.Minion;
public interface FieldSpell {
	public void performAction(ArrayList<Minion>field);
}
