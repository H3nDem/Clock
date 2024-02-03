package model.compteur;

import javafx.beans.property.IntegerProperty;

public class VariationIllimitee extends VariationStrategy {
	public VariationIllimitee(int init, SensChangement sensChangement) {
		super(init, sensChangement);
	}

	@Override
	public void gereChangement(IntegerProperty valeur) { }
}
