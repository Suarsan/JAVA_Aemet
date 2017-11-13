package dao;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import paa.municipios.IMunicipio;
import paa.municipios.IMunicipiosDAO;
import paa.municipios.IProvincia;

/**
 * Clase encargada de gestionar las provincias y municipios de la base de datos en el server mediante JSON
 * @author Javier Suarez
 */
public class DBRemoteAccessMunicipiosDAO implements IMunicipiosDAO{
	private URL url = null;
	private boolean ok = false;

	/**
	 * Constructor de la clase DBRemoteAccessMunicipiosDAO
	 */
	public DBRemoteAccessMunicipiosDAO(){}
	
	
	/**
	 * Añade un municipio a la base de datos
	 * @param municipio Instancia de la interfaz IMunicipio
	 * @return ok devuelve true si se ha completado con éxito
	 */
	public boolean addMunicipio(IMunicipio municipio){
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		try{
			url = new URL("http://localhost:8080/practica5/AddMunicipioServlet?name=" + municipio.getNombre().replace(" ","+")
					+"&provName=" + municipio.getProvincia().getNombre().replace(" ","+") 
					+"&provCode=" + municipio.getProvincia().getCodigo()
					+"&altitude=" + municipio.getAltitud()
					+"&code=" + municipio.getCodigo());
			try{
				 jsonObject = (JSONObject)parser.parse(new InputStreamReader(url.openStream()));
			}catch (ParseException | IOException e){
				e.printStackTrace();}
			ok = (boolean)jsonObject.get("success");
		}catch(Exception e){
			e.printStackTrace();}
		
		return ok;}
		
	/**
	 * Añade una provincia a la base de datos
	 * @param provincia Instancia de la interfaz IProvincia
	 * @return ok devuelve true si se ha completado con éxito 
	 */
	public boolean addProvincia(IProvincia provincia){
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		try{
			url = new URL("http://localhost:8080/practica5/AddProvinciaServlet?name=" + provincia.getNombre().replace(" ","+") 
					+"&code=" + provincia.getCodigo());
			try{
				 jsonObject = (JSONObject)parser.parse(new InputStreamReader(url.openStream()));
			}catch (ParseException | IOException e){
				e.printStackTrace();}
			ok = (boolean)jsonObject.get("success");
		}catch(Exception e){
			e.printStackTrace();}
		
		return ok;}
	
	/**
	 * Borra un municipio de la base de datos
	 * @param id Id del municipio
	 * @return ok devuelve true si se ha completado con éxito
	 */
	public boolean delMunicipio(int id){
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		try{
			url = new URL("http://localhost:8080/practica5/DelMunicipioServlet?id="+id);
			try{
				jsonObject = (JSONObject) parser.parse(new InputStreamReader(url.openStream()));
			}catch(ParseException | IOException e){
				e.printStackTrace();}
		}catch(Exception e){
			e.printStackTrace();}
		ok = (boolean) jsonObject.get("success");
		
		return ok;}
	
	/**
	 * Borra una provincia de la base de datos
	 * @param id Id de la provincia
	 * @return ok devuelve true si se ha completado con éxito
	 */
	public boolean delProvincia(int id){
		JSONObject jsonObject = null;
		JSONParser parser = new JSONParser();
		try{
			url = new URL("http://localhost:8080/practica5/DelProvinciaServlet?id=" + id);
			try{
				 jsonObject = (JSONObject)parser.parse(new InputStreamReader(url.openStream()));
			}catch (ParseException | IOException e){
				e.printStackTrace();}
			ok = (boolean)jsonObject.get("success");
		}catch(Exception e){
			e.printStackTrace();}
		
		return ok;}
	
	/**
	 * Devuelve un municipio de la base de datos - NO IMPLEMENTADO
	 * @param id Id del municipio
	 * @return municipio devuelve el municipio requerido - 
	 */
	public IMunicipio getMunicipio(int id){
		return null;}
	
	/**
	 * Devuelve un conjunto de municipios pertenecientes a una provincia de la base de datos
	 * @param idProvincia Id de la provincia a la que pertenecen los municipios requeridos
	 * @return conjuntoMunicipios devuelve el conjunto de municipios de la provincia requerida 
	 */
	public Collection<IMunicipio> getMunicipios(int idProvincia){
		Collection<IMunicipio> conjuntoMunicipios = new ArrayList<IMunicipio>();
		JSONArray jsonArray = null;
		JSONParser parser = new JSONParser();
	    JSONObject jsonObject = null;
	    //Conecta con el servlet y descarga un array con las provincias
		try{
			url = new URL ("http://localhost:8080/practica5/LeerMunicipiosServlet?id="+idProvincia);
			try{
				 jsonObject = (JSONObject)parser.parse(new InputStreamReader(url.openStream()));
			}catch (ParseException | IOException e){
				e.printStackTrace();}
			jsonArray = (JSONArray)jsonObject.get("municipios");
		}catch(Exception e){
			e.printStackTrace();}
		//Recoge los municipios del array y las introduce en una coleccion
		for (Object mun : jsonArray) {
			String nombre = (String) ((JSONObject)mun).get("nombre");
			JSONObject auxProvincia = (JSONObject) ((JSONObject)mun).get("provincia");
			String nomProvincia = (String) auxProvincia.get("nombre");
			long codProvincia = (long) auxProvincia.get("codigo");
			IProvincia prov = new Provincia (nomProvincia, (int)codProvincia);
			
			long altitud = (long) ((JSONObject)mun).get("altitud");
			long codigo = (long)((JSONObject)mun).get("codigo");
			conjuntoMunicipios.add(new Municipio(nombre,prov,(int)altitud,(int)codigo));}	
		
		return conjuntoMunicipios;}
	
	/**
	 * Devuelve el conjunto de provincias de la base de datos
	 * @return conjuntoProvincias devuelve el conjunto de provincias
	 */
	public Collection<IProvincia> getProvincias(){
		Collection<IProvincia> conjuntoProvincias= new ArrayList<IProvincia>();
		JSONArray jsonArray = null;
		JSONParser parser = new JSONParser();
	    JSONObject jsonObject = null;
	    //Conecta con el servlet y descarga un array con las provincias
		try{
			url = new URL ("http://localhost:8080/practica5/LeerProvinciasServlet");
			try{
				 jsonObject = (JSONObject)parser.parse(new InputStreamReader(url.openStream()));
			}catch (ParseException | IOException e){
				e.printStackTrace();}
			jsonArray = (JSONArray)jsonObject.get("provincias");
		}catch(Exception e){
			e.printStackTrace();}
		//Recoge las provincias del array y las introduce en una coleccion
		for (Object prov : jsonArray) {
			String nombre = (String) ((JSONObject)prov).get("nombre");
			long codigo = (long)((JSONObject)prov).get("codigo");
			conjuntoProvincias.add(new Provincia(nombre,(int)codigo));}
		
		return conjuntoProvincias;}
	
	/**
	 * Devuelve una provincia de la base de datos - NO IMPLEMENTADO
	 * @param id Id de la provincia requerida
	 * @return provincia devuelve la provincia requerida 
	 */
	public IProvincia getProvincia(int id){
		return null;}}
