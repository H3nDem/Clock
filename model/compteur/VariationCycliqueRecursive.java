package model.compteur;

import javafx.beans.property.IntegerProperty;

public class VariationCycliqueRecursive extends VariationCyclique {

	private Compteur suivant;

	public VariationCycliqueRecursive(int init, int borne, SensChangement sensChangement, Compteur suivant) {
		super(init, borne, sensChangement);
		this.suivant = suivant;
	}

	@Override
	public void gereChangement(IntegerProperty valeur) {
		if(valeur.get() == getBorne()) {
			valeur.set(getInit());
			suivant.tick();
		}
	}
}
