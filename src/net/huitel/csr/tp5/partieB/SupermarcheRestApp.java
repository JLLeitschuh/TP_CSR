package net.huitel.csr.tp5.partieB;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.huitel.csr.tp5.partieA.StructureSupermarche;
import net.huitel.csr.tp5.partieB.ressources.ResourceClient;
import net.huitel.csr.tp5.partieB.ressources.ResourceClients;
import net.huitel.csr.tp5.partieB.ressources.ResourceStocks;

public class SupermarcheRestApp extends Application {
	/**
	 * Reference vers la structure du supermarche
	 */
	StructureSupermarche mStructureSupermarche;

	public SupermarcheRestApp(StructureSupermarche structure, Context context) {
		super(context);
		mStructureSupermarche = structure;
	}

	/**
	 * Initialise le router pour qu'il redirige les requetes vers les bonnes
	 * ServerResources
	 */
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/supermarche/clients", ResourceClients.class);
		router.attach("/supermarche/clients/", ResourceClients.class);

		router.attach("/supermarche/clients/{id}", ResourceClient.class);
		router.attach("/supermarche/clients/{id}/", ResourceClient.class);

		router.attach("/supermarche/stock", ResourceStocks.class);
		router.attach("/supermarche/stock/", ResourceStocks.class);
		return router;
	}

	/**
	 * Verifie que l'uri passe en parametre se termine par le caractere '/'
	 * 
	 * @param uri
	 *            L'uri a verifier
	 * @return L'uri avec le caractere '/' ajoute si besoin
	 */
	public static String verifierUri(String uri) {
		return uri.charAt(uri.length() - 1) == '/' ? uri : uri + "/";
	}

	public StructureSupermarche getStructureSupermarche() {
		return mStructureSupermarche;
	}
}
