package model.compteur;

import javafx.beans.property.SimpleIntegerProperty;

public class Compteur {
	private final int init;
	private SimpleIntegerProperty valeur = new SimpleIntegerProperty();
	private final VariationStrategy variationStrategy;

	
	public int getInit() {
		return init;
	}

	public int getValeur() {
		return valeur.get();
	}
	
	public void setValeur(int valeur) {
		this.valeur.set(valeur);
	}
	
	public SimpleIntegerProperty valeurProperty() {
		return valeur;
	}

	public VariationStrategy getVariationStrategy() {
		return variationStrategy;
	}
	
	public Compteur(int init, VariationStrategy variationStrategy ){
		this.init = init;
		valeur.set(init);
		this.variationStrategy = variationStrategy;
	}
	
	public Compteur(int init){
		this(init, new VariationIllimitee(init, new Increment()));
	}
	
	public Compteur(){
		this(0);
	}
	
	public Compteur(int init, int borne) {
		this(init, new VariationCyclique(init, borne,
				(init < borne) ? new Increment() : new Decrement()));
	}
	
	public Compteur(int init, int borne, VariationStrategy variationStrategy) {
		this.init = init;
		this.variationStrategy = variationStrategy;
	}

	public void tick() {
		variationStrategy.change(valeur);
	}
}
