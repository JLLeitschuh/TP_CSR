package net.huitel.csr.tp5.partieB.ressources;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import net.huitel.csr.tp5.partieA.threads.Client;
import net.huitel.csr.tp5.partieB.ConstantesApiRest;
import net.huitel.csr.tp5.partieB.SupermarcheRestApp;;

/**
 * ServerResource utilisee lors d'une requete sur un client du supermarche
 * @author alan
 *
 */
public class ResourceClient extends ServerResource {

	/**
	 * Retourne le client correspondant a l'id donne dans l'URI
	 * 
	 * @return Representation JSON d'un client
	 * @throws JSONException
	 */
	@Get()
	public Representation getClient() throws JSONException {
		String idClientString = (String) getRequest().getAttributes().get(ConstantesApiRest.ID_CLIENT);
		int idClient = Integer.valueOf(idClientString);

		Client client = getClientAvecId(idClient);
		String url = SupermarcheRestApp.verifierUri(getReference().toString());
		// Si l'id du client recherche n'est pas present dans la liste de
		// clients, on force le code de retour 404
		if (client == null)
			this.getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		JSONObject clientJSON = JsonParser.clientVersJson(client, url);

		JsonRepresentation result = new JsonRepresentation(clientJSON);
		result.setIndenting(true);
		return result;
	}

	/**
	 * Recherche le client dont l'id correspond a celui passe en parametre
	 * 
	 * @param idClient
	 *            Id du client a trouver dans la liste de clients du supermarche
	 * @return Client correspondant a l'id en parametre
	 */
	private Client getClientAvecId(int idClient) {
		Client client;
		ArrayList<Client> clients = ((SupermarcheRestApp) getApplication()).getStructureSupermarche().getClients();
		for (int i = 0; i < clients.size(); i++) {
			client = clients.get(i);
			if (client.getIdClient() == idClient)
				return client;
		}
		return null;
	}

}
