package net.huitel.csr.tp5.partieB.resources;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import net.huitel.csr.tp5.StructureSupermarche;
import net.huitel.csr.tp5.partieA.threads.Client;
import net.huitel.csr.tp5.partieB.SupermarcheRestApp;

/**
 * ServerResource utilisee lors d'une requete de consultation de l'ensemble des
 * clients du supermarche ou de creation d'un nouveau client
 * 
 * @author alan
 *
 */
public class ResourceClients extends ServerResource {

	/**
	 * Retourne la liste des clients du supermarche
	 *
	 * @return Representation JSON des clients du supermarche
	 * @throws JSONException
	 */
	@Get()
	public Representation getClients() throws JSONException {

		StructureSupermarche structureSupermarche = ((SupermarcheRestApp) getApplication()).getStructureSupermarche();
		ArrayList<Client> clients = structureSupermarche.getClients();
		String url = getReference().toString();

		JSONArray jsonArray = JsonParser.clientsVersJson(clients, url);
		JsonRepresentation result = new JsonRepresentation(jsonArray);
		result.setIndenting(true);
		return result;
	}

	/**
	 * Instancie un nouveau client et le renvoie
	 * 
	 * @return Representation JSON du client cree
	 * @throws JSONException
	 */
	@Post()
	synchronized public Representation creerClient() throws JSONException {
		StructureSupermarche structureSupermarche = ((SupermarcheRestApp) getApplication()).getStructureSupermarche();
		Client client;

		// Instanciation et lancement d'un nouveau thread client
		client = new Client(structureSupermarche, creerIdClientUnique(structureSupermarche.getClients().size()));
		client.start();

		// Ajout du thread client a la liste de clients du supermarche.
		structureSupermarche.getClients().add(client);

		String uri = SupermarcheRestApp.verifierUri(getReference().toString()) + client.getIdClient();
		JsonRepresentation result = new JsonRepresentation(JsonParser.clientVersJson(client, uri));
		result.setIndenting(true);
		return result;
	}

	/**
	 * Methode recursive retournant un id superieur a l'id en parametre,
	 * qu'aucun client dans la liste de clients ne possede.
	 * 
	 * @param id
	 *            Id a verifier et a incrementer si besoin
	 * @return id non deja present dans la liste de clients du supermarche
	 */
	private int creerIdClientUnique(int id) {
		List<Client> clients = ((SupermarcheRestApp) getApplication()).getStructureSupermarche().getClients();
		int idUnique = id;
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getId() == idUnique) {
				idUnique++;
				return creerIdClientUnique(idUnique);
			}
		}
		return idUnique;
	}

}
