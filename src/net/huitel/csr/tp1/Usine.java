package net.huitel.csr.tp1;
// PENSEZ A INDIQUER PAR DES COMMENTAIRES LES MODIFICATIONS APPORTEES A CE SQUELETTE AU FUR

// ET A MESURE DE L'EVOLUTION DU CODE DEMANDEE DANS LE TP.

import java.util.Date;

/**
 * Les objets instances de la classe Usine represente une usine avec deux
 * ateliers. Une instance d'Usine possede un stock de pieces a transformer ainsi
 * qu'un stock de pieces finies initialement vide. Chacun des deux ateliers
 * transforme la moitie des unites du stock a transformer. La methode
 * fonctionner() fait travailler successivement les deux ateliers et affiche
 * l'etat des stocks a la fin des travaux.
 */
class Usine {
	/**
	 * Stock de pieces a transformer
	 */
	Stock stockDepart = new Stock("de depart", 10);

	Stock stockIntermediaire;
	/**
	 * Stock de pieces transformees
	 */
	Stock stockFin = new Stock("d'arrivee", 0);
	/**
	 * Ateliers de transformation
	 */
	Atelier atelier1;
	Atelier atelier2;
	Atelier atelier3;
	Atelier atelier4;

	/**
	 * Version de l'Usine, correspondant au cas 1, 2 ou 3
	 */
	private int mVersion;

	/**
	 * Constructeur d'une Usine Cas 1: 2 Ateliers travaillent sur un stock de
	 * départ et un stock d'arrivée. Cas 2: 2 Ateliers travaillent sur un stock
	 * de départ, un stock intermédiaire et un stock d'arrivée. Cas 3: 3
	 * Ateliers travaillent sur un stock de départ, un stock intermédiaire et un
	 * stock d'arrivée. Les deux ateliers travaillant sur les stocks
	 * intermédiaire et de fin se partagent la charge de travail. Cas 4: 2
	 * Ateliers travaillent sur un stock de départ et un stock intermédiaire, 2
	 * autres à ateliers travaillent sur le stock intermédiaire et un stock de
	 * fin. Les Ateliers travaillant sur les mêmes stocks se partagent la charge
	 * de travail. Le stock intermédaire a une capacité limitée à 1.
	 * 
	 * @param version
	 *            Version de l'Usine, désignant chaque cas.
	 */
	public Usine(int version) {
		mVersion = version;
		switch (version) {
		case Constantes.VERSION1:
			stockIntermediaire = new Stock("intermediaire", 0);
			atelier1 = new Atelier(stockDepart, stockFin, 5, Constantes.VERSION1);
			atelier2 = new Atelier(stockDepart, stockFin, 5, Constantes.VERSION1);
			break;
		case Constantes.VERSION2:
			stockIntermediaire = new Stock("intermediaire", 0);
			atelier1 = new Atelier(stockDepart, stockIntermediaire, 10, Constantes.VERSION2);
			atelier2 = new Atelier(stockIntermediaire, stockFin, 10, Constantes.VERSION2);
			break;
		case Constantes.VERSION3:
			stockIntermediaire = new Stock("intermediaire", 0);
			atelier1 = new Atelier(stockDepart, stockIntermediaire, 10, Constantes.VERSION3);
			atelier2 = new Atelier(stockIntermediaire, stockFin, 5, Constantes.VERSION3);
			atelier3 = new Atelier(stockIntermediaire, stockFin, 5, Constantes.VERSION3);
			break;
		case Constantes.VERSION4:
			stockIntermediaire = new Stock("intermediaire", 0, Constantes.STOCK_MAX_VERSION4);
			atelier1 = new Atelier(stockDepart, stockIntermediaire, 5, Constantes.VERSION4);
			atelier2 = new Atelier(stockDepart, stockIntermediaire, 5, Constantes.VERSION4);
			atelier3 = new Atelier(stockIntermediaire, stockFin, 5, Constantes.VERSION4);
			atelier4 = new Atelier(stockIntermediaire, stockFin, 5, Constantes.VERSION4);
			break;
		default:
			System.out.println(
					"Utilisation: new Usine(version), 1<=version<=4, sinon, une usine de type 4 est initialisée");
			stockIntermediaire = new Stock("intermediaire", 0, Constantes.STOCK_MAX_VERSION4);
			atelier1 = new Atelier(stockDepart, stockIntermediaire, 5, Constantes.VERSION4);
			atelier2 = new Atelier(stockDepart, stockIntermediaire, 5, Constantes.VERSION4);
			atelier3 = new Atelier(stockIntermediaire, stockFin, 5, Constantes.VERSION4);
			atelier4 = new Atelier(stockIntermediaire, stockFin, 5, Constantes.VERSION4);
			break;
		}
	}

	/**
	 * Constructeur de Usine. Créer une Usine du premier cas
	 */
	public Usine() {
		this(Constantes.VERSION1);
	}

	/**
	 * Effectuer le travail de l'usine Utilise successivement chaque atelier
	 * pour transformer une piece et affiche l'evolution de l'etat des stocks.
	 */
	public void fonctionner() {
		switch (mVersion) {
		case Constantes.VERSION1:
			fonctionnerCas1();
			break;
		case Constantes.VERSION2:
			fonctionnerCas2();
			break;
		case Constantes.VERSION3:
			fonctionnerCas3();
			break;
		case Constantes.VERSION4:
			fonctionnerCas4();
			break;
		default:
			break;
		}
	}

	
	/**
	 * Méthode de fonctionnement d'une Usine dans le cas 1.
	 */
	private void fonctionnerCas1() {
		// On initialise les time stamps des ateliers pour qu'ils puissent
		// calculer leur temps d'exécution.
		long curTime = new Date().getTime();
		atelier1.setTimeStamp(curTime);
		atelier2.setTimeStamp(curTime);
		// On lance les Ateliers (les Threads)
		atelier1.start();
		atelier2.start();

		try {
			atelier1.join();
			atelier2.join();
		} catch (InterruptedException e) {
			System.out.println("Erreur pendant l'attente de fin de travail des ateliers");
		}
		System.out.println(Constantes.AFFICHAGE_VERSION1);
		// La durée d'exécution correspond au temps le plus long entre les
		// deux Ateliers qui travaillent en même temps.
		long dureeExec = Math.max(atelier1.getTimeStamp(), atelier2.getTimeStamp());
		System.out.println("Temps d'exécution: " + dureeExec + " ms (Atelier1: " + atelier1.getTimeStamp()
				+ "ms et Atelier2: " + atelier2.getTimeStamp() + "ms)");

		// Affichage de l'état des stocks
		stockDepart.afficher();
		stockFin.afficher();
	}

	
	/**
	 * Méthode de fonctionnement d'une Usine dans le cas 2.
	 */
	private void fonctionnerCas2() {
		long curTime = new Date().getTime();
		atelier1.setTimeStamp(curTime);
		atelier2.setTimeStamp(curTime);

		atelier1.start();
		atelier2.start();

		try {
			atelier1.join();
			atelier2.join();
		} catch (InterruptedException e) {
			System.out.println("Erreur pendant l'attente de fin de travail des ateliers");
		}
		System.out.println(Constantes.AFFICHAGE_VERSION2);

		long dureeExec = Math.max(atelier1.getTimeStamp(), atelier2.getTimeStamp());
		System.out.println("Temps d'exécution: " + dureeExec + " ms (Atelier1: " + atelier1.getTimeStamp()
				+ "ms et Atelier2: " + atelier2.getTimeStamp() + "ms)");

		stockDepart.afficher();
		stockIntermediaire.afficher();
		stockFin.afficher();
	}

	/**
	 * Méthode de fonctionnement d'une Usine dans le cas 3.
	 */
	private void fonctionnerCas3() {
		long curTime = new Date().getTime();
		atelier1.setTimeStamp(curTime);
		atelier2.setTimeStamp(curTime);
		atelier3.setTimeStamp(curTime);
		
		atelier2.start();
		atelier3.start();
		atelier1.start();
		try {
			atelier2.join();
			atelier3.join();
			atelier1.join();
		} catch (InterruptedException e) {
		}
		System.out.println(Constantes.AFFICHAGE_VERSION3);
		
		long dureeExec = Math.max(Math.max(atelier1.getTimeStamp(), atelier2.getTimeStamp()), atelier3.getTimeStamp());
		System.out.println("Temps d'exécution: " + dureeExec + " ms (Atelier1: " + atelier1.getTimeStamp() + "ms" + "\t"
				+ "Atelier2: " + atelier2.getTimeStamp() + "ms" + "\t" + "Atelier3: " + atelier3.getTimeStamp()
				+ "ms)");
		
		stockDepart.afficher();
		stockIntermediaire.afficher();
		stockFin.afficher();
	}

	/**
	 * Méthode de fonctionnement d'une Usine dans le cas 4.
	 */
	private void fonctionnerCas4() {
		long curTime = new Date().getTime();
		atelier1.setTimeStamp(curTime);
		atelier2.setTimeStamp(curTime);
		atelier3.setTimeStamp(curTime);
		atelier4.setTimeStamp(curTime);
		
		atelier1.start();
		atelier2.start();
		atelier3.start();
		atelier4.start();
		try {
			atelier1.join();
			atelier2.join();
			atelier3.join();
			atelier4.join();
		} catch (InterruptedException e) {
		}
		System.out.println(Constantes.AFFICHAGE_VERSION4);
		
		long dureeExec = Math.max(Math.max(atelier3.getTimeStamp(), atelier4.getTimeStamp()),
				Math.max(atelier1.getTimeStamp(), atelier2.getTimeStamp()));
		System.out.println("Temps d'exécution: " + dureeExec + " ms (Atelier1: " + atelier1.getTimeStamp() + "ms"
				+ "\t" + "Atelier2: " + atelier2.getTimeStamp() + "ms" + "\t" + "Atelier3: " + atelier3.getTimeStamp()
				+ "ms" + "\t" + "Atelier4: " + atelier4.getTimeStamp() + "ms)");

		stockDepart.afficher();
		stockIntermediaire.afficher();
		stockFin.afficher();
	}

	/**
	 * Point d'entree pour l'ensemble du TP.
	 * 
	 * @param args
	 *            Non utilise
	 */
	public static void main(String[] args) {
		Usine usine = new Usine(Constantes.VERSION4);
		usine.fonctionner();

	}
}
