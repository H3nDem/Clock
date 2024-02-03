package model.compteur;

import javafx.beans.property.IntegerProperty;

public class VariationCyclique extends VariationStrategy {
	private int borne;
	
	public int getBorne() {
		return borne;
	}
	
	public VariationCyclique(int init, int borne, SensChangement sensChangement) {
		super(init, sensChangement);
		this.borne = borne;
	}

	@Override
	public void gereChangement(IntegerProperty valeur) {
		if(valeur.get() == getBorne())
			valeur.set(getInit());
	}
}
