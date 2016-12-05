package net.huitel.csr.tp5.partieB.resources;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import net.huitel.csr.tp5.StructureSupermarche;
import net.huitel.csr.tp5.partieA.Rayon;
import net.huitel.csr.tp5.partieB.SupermarcheRestApp;

/**
 * ServerResource utilisee lors d'une requete sur l'etat des stocks du supermarche
 * @author alan
 *
 */
public class ResourceStocks extends ServerResource {

	/**
	 * Retourne une representation JSON du stock des rayons du supermarche
	 * 
	 * @return Representation JSON de l'etat du stock des rayons du supermarche
	 * @throws JSONException
	 */
	@Get()
	public Representation getStocks() throws JSONException {
		StructureSupermarche structureSupermarche = ((SupermarcheRestApp) getApplication()).getStructureSupermarche();
		List<Rayon> rayons = structureSupermarche.getRayons();

		JSONArray jsonArray = JsonParser.rayonsVersJson(rayons);

		JsonRepresentation result = new JsonRepresentation(jsonArray);
		result.setIndenting(true);
		return result;
	}
}
