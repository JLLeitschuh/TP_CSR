package net.huitel.csr.tp5.partieB.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.huitel.csr.tp5.partieA.Rayon;
import net.huitel.csr.tp5.partieA.threads.Client;
import net.huitel.csr.tp5.partieB.ConstantesApiRest;

/**
 * Classe contenant des methodes static permettant de generer des JSONObject ou
 * des JSONArray a partir d'objets du supermarche
 * 
 * @author alan
 *
 */
public class JsonParser {

	/**
	 * Retourne un JSONObject du client passe en parametre
	 * 
	 * @param client
	 *            Client a modeliser en JSON
	 * @param uri
	 *            Uri du client
	 * @return JSONObject du client
	 * @throws JSONException
	 */
	public static JSONObject clientVersJson(Client client, String uri) throws JSONException {
		JSONObject clientJSON = new JSONObject();
		if (client != null) {
			clientJSON.put(ConstantesApiRest.ID_CLIENT, client.getIdClient());
			clientJSON.put(ConstantesApiRest.LISTE_CLIENT, client.getListeCourses());
			clientJSON.put(ConstantesApiRest.ETAT_CLIENT, client.getEtat());
			clientJSON.put(ConstantesApiRest.URL_CLIENT, uri);
		}
		return clientJSON;
	}

	/**
	 * Retourne une JSONArray de la liste de clients passee en parametre
	 * 
	 * @param clients
	 *            Liste de clients a modeliser en JSON
	 * @param uri
	 *            Uri des clients du supermarche
	 * @return JSONArray des clients
	 * @throws JSONException
	 */
	public static JSONArray clientsVersJson(List<Client> clients, String uri) throws JSONException {
		Collection<JSONObject> clientsJson = new ArrayList<JSONObject>();
		for (Client client : clients) {
			JSONObject clientJSON = new JSONObject();
			clientJSON.put(ConstantesApiRest.ID_CLIENT, client.getIdClient());
			clientJSON.put(ConstantesApiRest.URL_CLIENT, uri + client.getIdClient());
			clientsJson.add(clientJSON);
		}
		JSONArray jsonArray = new JSONArray(clientsJson);
		return jsonArray;
	}

	/**
	 * Retourne une JSONArray de la liste de rayons passee en parametre
	 * @param rayons Liste de rayons a modeliser en JSON
	 * @return JSONArray des rayons
	 * @throws JSONException
	 */
	public static JSONArray rayonsVersJson(List<Rayon> rayons) throws JSONException {
		Collection<JSONObject> rayonsJson = new ArrayList<JSONObject>();

		for (Rayon rayon : rayons) {
			JSONObject rayonJSON = new JSONObject().put(rayon.getProduitContenu().toString(), rayon.getStock());
			rayonsJson.add(rayonJSON);
		}

		JSONArray jsonArray = new JSONArray(rayonsJson);
		return jsonArray;
	}
}
