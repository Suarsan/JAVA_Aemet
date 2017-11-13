package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import paa.municipios.IMunicipio;
import paa.municipios.IMunicipiosDAO;
import paa.municipios.IProvincia;

/**
 * Clase encargada de gestionar las provincias y municipios de la base de datos Access
 * @author Suarez
 */
public class DBAccessMunicipiosDAO implements IMunicipiosDAO{
	private String sql, url = "jdbc:ucanaccess://webapps/practica5/WEB-INF/bbdd/PAA_Municipios.mdb";

	/**
	 * Constructor de la clase DBAccessMunicipiosDAO
	 */
	public DBAccessMunicipiosDAO(){}
	
	
	/**
	 * A&ntildeade un municipio a la tabla 'Municipios' de la base de datos 'GestionMunicipios'
	 * @param municipio Instancia de la interfaz IMunicipio
	 * @return ok devuelve true si se ha actualizado un solo registro de la tabla 
	 */
	public boolean addMunicipio(IMunicipio municipio){
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "INSERT INTO Municipios (IdProvincia, Municipio, Altitud, IdMunicipio) VALUES ('"+municipio.getProvincia().getCodigo()+"','"+municipio.getNombre()+"','"+municipio.getAltitud()+"','"+municipio.getCodigo()+"');";
					if(consulta.executeUpdate(sql) == 1){
						return true;}
		}}catch(Exception e){
			e.printStackTrace();}
		return false;}
		
	/**
	 * A&ntildeade una provincia a la tabla 'Provincias' de la base de datos 'GestionMunicipios'
	 * @param provincia Instancia de la interfaz IProvincia
	 * @return ok devuelve true si se ha actualizado un solo registro de la tabla 
	 */
	public boolean addProvincia(IProvincia provincia){
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "INSERT INTO Provincias (IdProvincia, Provincia) VALUES ('"+provincia.getCodigo()+"','"+provincia.getNombre()+"');";
					if(consulta.executeUpdate(sql) == 1){
						return true;}
		}}catch(Exception e){
			e.printStackTrace();}
		return false;}
	
	/**
	 * Borra un municipio de la tabla 'Municipios' de la base de datos 'GestionMunicipios'
	 * @param id Id del municipio
	 * @return ok devuelve true si ha actualizado un solo registro de la tabla 
	 */
	public boolean delMunicipio(int id){
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "DELETE FROM Municipios WHERE IdMunicipio =" +id+ ";";
					if(consulta.executeUpdate(sql) == 1){
						return true;}
		}}catch(Exception e){
			e.printStackTrace();}
		return false;}
	
	/**
	 * Borra una provincia de la tabla 'Provincias' de la base de datos 'GestionMunicipios'
	 * @param id Id de la provincia
	 * @return ok devuelve true si ha actualizado un solo registro de la tabla 
	 */
	public boolean delProvincia(int id){
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "DELETE FROM Municipios WHERE IdProvincia =" +id+ ";";
					consulta.executeUpdate(sql);
				try(Statement consulta2 = conexion.createStatement()){
					sql = "DELETE FROM Provincias WHERE IdProvincia =" +id+ ";";
					if(consulta2.executeUpdate(sql) == 1){
						return true;}
		}}}catch(Exception e){
			e.printStackTrace();}
		return false;}
	
	/**
	 * Devuelve un municipio de la tabla 'Municipios' de la base de datos 'GestionMunicipios'
	 * @param id Id del municipio
	 * @return municipio devuelve el municipio requerido 
	 */
	public IMunicipio getMunicipio(int id){
		IProvincia provincia = null;
		IMunicipio municipio = null;
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "SELECT * FROM Municipios INNER JOIN Provincias ON Municipios.IdProvincia = Provincias.IdProvincia AND IdMunicipio ="+id+";";
					try(ResultSet query = consulta.executeQuery(sql)){
						while(query.next()){
							provincia = new Provincia(query.getString("Provincia"),query.getInt("IdProvincia"));
							municipio = new Municipio(query.getString("Municipio"), provincia, query.getInt("Altitud"), query.getInt("IdMunicipio"));}
		}}}catch(Exception e){
			e.printStackTrace();}
		return municipio;}
	
	/**
	 * Devuelve un conjunto de municipios pertenecientes a una provincia de la tabla 'Municipios' de la base de datos 'GestionMunicipios'
	 * @param idProvincia Id de la provincia a la que pertenecen los municipios requeridos
	 * @return conjuntoMunicipios devuelve el conjunto de municipios de la provincia requerida 
	 */
	public Collection<IMunicipio> getMunicipios(int idProvincia){
		Collection<IMunicipio> conjuntoMunicipios = new ArrayList<IMunicipio>();
		IProvincia provincia = null;
		IMunicipio municipio = null;
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "SELECT * FROM Municipios INNER JOIN Provincias ON Municipios.IdProvincia = Provincias.IdProvincia AND Municipios.IdProvincia = " + idProvincia + " ORDER BY Municipio;";
					try(ResultSet query = consulta.executeQuery(sql)){
						while(query.next()){
							provincia = new Provincia(query.getString("Provincia"),idProvincia);
							municipio = new Municipio(query.getString("Municipio"), provincia, query.getInt("Altitud"),query.getInt("IdMunicipio"));
							conjuntoMunicipios.add(municipio);}
		}}}catch(Exception e){
			e.printStackTrace();}
		return conjuntoMunicipios;}
	
	/**
	 * Devuelve el conjunto de provincias de la tabla 'Provincias' de la base de datos 'GestionMunicipios'
	 * @return conjuntoProvincias devuelve el conjunto de provincias 
	 */
	public Collection<IProvincia> getProvincias(){
		Collection<IProvincia> conjuntoProvincias = new ArrayList<IProvincia>();
		IProvincia provincia = null;
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "SELECT * FROM Provincias ORDER BY IdProvincia;";
					try(ResultSet query = consulta.executeQuery(sql)){
						while(query.next()){
							provincia = new Provincia(query.getString("Provincia"), query.getInt("IdProvincia"));
							conjuntoProvincias.add(provincia);}
		}}}catch(Exception e){
			e.printStackTrace();}
		return conjuntoProvincias;}
	
	/**
	 * Devuelve una provincia de la tabla 'Provincias' de la base de datos 'GestionMunicipios'
	 * @param id Id de la provincia requerida
	 * @return provincia devuelve la provincia requerida 
	 */
	public IProvincia getProvincia(int id){
		IProvincia provincia = null;
			try(Connection conexion = DriverManager.getConnection(url)){
				try(Statement consulta = conexion.createStatement()){
					sql = "SELECT * FROM Provincias WHERE IdProvincia ="+id+";";
					try(ResultSet query = consulta.executeQuery(sql)){
						while(query.next()){
							provincia = new Provincia(query.getString("Provincia"), query.getInt("IdProvincia"));}
		}}}catch(Exception e){
			e.printStackTrace();}
		return provincia;}}
