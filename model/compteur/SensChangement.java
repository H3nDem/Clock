package model.compteur;

import javafx.beans.property.IntegerProperty;

public interface SensChangement {
	void change(IntegerProperty valeur);
}
