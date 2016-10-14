package net.huitel.csr.tp1;
// PENSEZ A INDIQUER PAR DES COMMENTAIRES LES MODIFICATIONS APPORTEES A CE SQUELETTE AU FUR

// ET A MESURE DE L'EVOLUTION DU CODE DEMANDEE DANS LE TP.

/**
 * Les objets instances de la classe Stock representent un ensemble de pieces,
 * empilees les unes sur les autres. Du fait de la disposition en piles, il
 * n'est pas possible que deux operateurs saisissent deux pieces au meme moment.
 *
 */
class Stock {
	/**
	 * Nombre de pieces dans la pile
	 */
	private int nbPieces;
	/**
	 * Le nom du stock
	 */
	private String nom;

	private int stockMax;

	/**
	 * Creer un nouvel objet instance de stock
	 * 
	 * @param nom
	 *            Le nom du nouveau stock
	 * @param nbPieces
	 *            Le nombre de pieces initial
	 */
	public Stock(String nom, int nbPieces) {
		this.nbPieces = nbPieces;
		this.nom = nom;
	}

	public Stock(String nom, int nbPieces, int stockMax) {
		this(nom, nbPieces);
		this.stockMax = stockMax;
	}

	/**
	 * Poser une piece sur le haut de la pile de pieces Cas 1: 2 Ateliers
	 * travaillent sur un stock de départ et un stock d'arrivée.
	 */
	synchronized public void stocker() {
		nbPieces++;
	}

	/**
	 * Poser une piece sur le haut de la pile de pieces Cas 2: 2 Ateliers
	 * travaillent sur un stock de départ, un stock intermédiaire et un stock
	 * d'arrivée. Il faut signaler aux Ateliers en attente de déstockage que le
	 * nombre de pièces a été incrémenté.
	 */
	synchronized public void stocker2() {
		nbPieces++;
		notify();
	}

	synchronized public void stocker3() {
		nbPieces++;
		notifyAll();
	}

	synchronized public void stocker4() {
		while (nbPieces == stockMax)
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException");
			}
		nbPieces++;
		notifyAll();
	}

	/**
	 * Saisir une piece sur le haut de la pile de pieces Cas 1: 2 Ateliers
	 * travaillent sur un stock de départ et un stock d'arrivée. Il ne faut pas
	 * qu'un Atelier déstocke un Stock vide.
	 */
	synchronized public void destocker() {
		if (nbPieces != 0)
			nbPieces--;

	}

	/**
	 * Cas 2: 2 Ateliers travaillent sur un stock de départ, un stock
	 * intermédiaire et un stock d'arrivée. Il ne faut pas qu'un atelier
	 * déstocke un Stock vide (typiquement, le stock intermédiaire qui est vide
	 * au lancement de la transformation).
	 */
	synchronized public void destocker2() {
		if (nbPieces == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException");
			}
		}
		nbPieces--;
	}

	/**
	 * Cas 3: 3 Ateliers travaillent sur un stock de départ, un stock
	 * intermédiaire et un stock d'arrivée. Les deux ateliers travaillant sur
	 * les stocks intermédiaire et de fin se partagent la charge de travail.
	 */
	synchronized public void destocker3() {
		while (nbPieces == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException");
			}
		nbPieces--;
	}

	synchronized public void destocker4() {
		while (nbPieces == 0)
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("InterruptedException");
			}
		nbPieces--;
		notifyAll();
	}

	/**
	 * Affiche l'etat de l'objet stock
	 */
	public void afficher() {
		System.out.println(toString());
	}

	public String toString() {
		return "Le stock " + nom + " contient " + nbPieces + " piece(s).";
	}

	/**
	 * Methode d'auto-test pour la classe Stock
	 * 
	 * @param args
	 *            Non utilise
	 */
	static public void main(String[] args) {

	}
}
