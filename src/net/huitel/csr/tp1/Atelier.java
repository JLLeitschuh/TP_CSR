package net.huitel.csr.tp1;

import java.util.Date;

// PENSEZ A INDIQUER PAR DES COMMENTAIRES LES MODIFICATIONS APPORTEES A CE SQUELETTE AU FUR
// ET A MESURE DE L'EVOLUTION DU CODE DEMANDEE DANS LE TP.

/**
 * Les objets instances de la classe Atelier representent des ateliers de
 * transformation. Le fonctionnement est le suivant : l'appel a transformer
 * retire un element du stock A, attend 100 ms, puis ajoute un element au stock
 * B. La methode travailler() effectue n transformations successives, n etant un
 * parametre fourni a la creation de l'objet.
 */
class Atelier extends Thread {

	/**
	 * Le stock de fourniture de depart
	 */
	private Stock A;
	/**
	 * Le stock de produits transformes
	 */
	private Stock B;
	/**
	 * Le nombre de transformations effectuees lors d'un appel a la methode
	 * travailler().
	 */
	private int nbTransfo;

	/**
	 * Version de l'Usine appelante, correspondant au cas 1, 2 ou 3
	 */
	private static int mVersion;

	/**
	 * Time stamp pour calculer le temps d'exécution des Ateliers
	 */
	private long timeStamp;

	void setTimeStamp(long time) {
		timeStamp = time;
	}

	long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Construit un objet instance d'Atelier
	 * 
	 * @param A
	 *            Le stock de pieces de depart
	 * @param B
	 *            Le stock de pieces transformees
	 * @param nbTransfo
	 *            Le nombre de transformations par appel a travailler()
	 */
	public Atelier(Stock A, Stock B, int nbTransfo, int version) {
		this.A = A;
		this.B = B;
		this.nbTransfo = nbTransfo;
		mVersion = version;
	}
	

	/**
	 * Effectue une transformation
	 */
	public void transformer() {
		A.destocker();
		try { Thread.sleep(100); } catch(InterruptedException e) {}
		B.stocker();
	}

	/**
	 * Effectue une transformation dans le cas numéro 2
	 */
	public void transformer2() {
		A.destocker2();
		try { Thread.sleep(100); } catch(InterruptedException e) {}
		B.stocker2();
	}

	/**
	 * Effectue une transformation dans le cas numéro 3
	 */
	public void transformer3() {
		A.destocker3();
		try { Thread.sleep(100); } catch(InterruptedException e) {}
		B.stocker3();
	}

	/**
	 * Effectue une transformation dans le cas numéro 4
	 */
	void transformer4() {
		A.destocker4();
		B.stocker4();
	}

	/**
	 * Effectue nbTransfo transformations
	 */
	@Override
	public void run() {// Refactor: renommage de travailler() pour adapter au
						// Thread
		for (; nbTransfo > 0; nbTransfo--) {
			System.out.println(
					Thread.currentThread().getName() + ": " + A.toString() + "\t(nbTransfo=" + nbTransfo + ")");
			switch (mVersion) {
			case Constantes.VERSION1:
				transformer();
				break;
			case Constantes.VERSION2:
				transformer2();
				break;
			case Constantes.VERSION3:
				transformer3();
				break;
			case Constantes.VERSION4:
				transformer4();
				break;
			default:
				transformer();
				break;
			}
		}
		// On met à jour le timestamp pour savoir le temps d'exécution de la
		// boucle de transformation
		timeStamp = new Date().getTime() - timeStamp;
	}

	/**
	 * Methode d'auto-test pour la classe Atelier
	 * 
	 * @param args
	 *            Non utilise
	 */
	static public void main(String[] args) {
		Stock stockDepart = new Stock("StockDepart", 10);
		Stock stockArrivee = new Stock("StockArrivee", 0);
		final int nbTransfo = 10;
		new Atelier(stockDepart, stockArrivee, nbTransfo, Constantes.VERSION1).start();
		/*
		 * 
		 * Stock stockDarty = new Stock("Darty", 5000); Stock stockFnac = new
		 * Stock("Fnac", 5);
		 * 
		 * Atelier atelier = new Atelier(stockDarty, stockFnac, 1);
		 * 
		 * for( int i =0; i<=98000; i++){ System.out.println("Le stock + ");
		 * stockDarty.stocker(); stockDarty.stocker(); stockDarty.stocker();
		 * stockDarty.stocker(); System.out.println("Le stock - ");
		 * stockDarty.destocker(); stockDarty.destocker();
		 * stockDarty.destocker(); stockDarty.destocker();
		 * stockDarty.afficher();
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * System.out.println("Le stock final est de  "); stockDarty.afficher();
		 * 
		 * atelier.transformer(); stockDarty.afficher(); stockFnac.afficher();
		 * atelier.travailler(); stockDarty.afficher(); stockFnac.afficher();
		 */

	}
}
