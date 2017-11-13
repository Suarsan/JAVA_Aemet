package dao;
import java.lang.String;

/**
 * Clase encargada de gestionar una provincia.
 * @author Javier Suarez Sanchez
 */
public class Provincia implements paa.municipios.IProvincia{
	private String nombre;
	private int codigo;
	
	
	/**
	 * Constructor de la clase dado un nombre y un código
	 * @param nombre Nombre de la provincia
	 * @param codigo C&oacutedigo de la provincia
	 */
	public Provincia (String nombre, int codigo){
		this.nombre = nombre;
		this.codigo = codigo;}
	
	
	/**
	 * Devuelve el c&oacutedigo de la provincia
	 * @return codigo de la provincia*/
	public int getCodigo(){ 
		return codigo;}
	/**
	 * Devuelve el nombre de la provincia
	 * @return nombre de la provincia
	 */
	public String getNombre(){
		return nombre;}
	/**
	 * Actualiza el c&oacutedigo de la provincia
	 * @param codigo C&oacutedigo de la provincia
	 */
	public void setCodigo(int codigo){
		this.codigo = codigo;}
	/**
	 * Actualiza el nombre de la provincia
	 * @param nombre Nombre de la provincia
	 */
	public void setNombre(String nombre){
		this.nombre = nombre;}
	/**
	 * Devuelve un string con el c&oacutedigo y el nombre de la provincia
	 */
	public String toString(){
		return nombre;}}
