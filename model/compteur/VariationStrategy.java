package model.compteur;

import javafx.beans.property.IntegerProperty;

public abstract class VariationStrategy {
	private int init;
	private SensChangement sensChangement;
	
	public int getInit() {
		return init;
	}
	
	public VariationStrategy(int init, SensChangement sensChangement) {
		this.sensChangement = sensChangement;
		this.init = init;
	}

	public void change(IntegerProperty valeur) {
		sensChangement.change(valeur);
		gereChangement(valeur);
	}
	
	abstract public void gereChangement(IntegerProperty valeur);
}
