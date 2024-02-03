package model.compteur;

import javafx.beans.property.IntegerProperty;

public class Decrement implements SensChangement {

	@Override
	public void change(IntegerProperty valeur) {
		valeur.set(valeur.get() - 1);
	}
}
