package model.compteur;

public class CompteurCompose extends Compteur {
	private Compteur suivant;

	public Compteur getSuivant() {
		return suivant;
	}

	public void setSuivant(Compteur suivant) {
		this.suivant = suivant;
	}
	
	public CompteurCompose(int init, int borne, Compteur suivant){
		super(init, borne, new VariationCycliqueRecursive(init, borne, init < borne ? new Increment() : new Decrement(), suivant));
		this.suivant = suivant;
	}
	
	
}
