package dao;
import paa.municipios.IProvincia;
import java.lang.String;

/**
 * Clase encargada de gestionar un municipio.
 * @author Javier Suárez Sánchez
 */
public class Municipio implements paa.municipios.IMunicipio{
	private String nombre;
	private int altitud, codigo;
	private IProvincia provincia;
	
	
	/**
	 * Constructor de la clase dado un nombre, una provincia, una altitud y un código
	 * @param nombre Nombre del municipio
	 * @param provincia Instancia de la interfaz provincia a la que pertenece el municipio
	 * @param altitud Altitud a la que se encuentra el municipio
	 * @param codigo C&oacutedigo postal del municipio */
	public Municipio(String nombre, IProvincia provincia, int altitud, int codigo){
		this.nombre = nombre;
		this.provincia = provincia;
		this.altitud = altitud;
		this.codigo = codigo;}
	
	
	/**
	 * Devuelve la altitud del municipio
	 * @return altitud es la altitud del municipio */
	public int getAltitud(){
		return altitud;}
	/**
	 * Devuelve el código postal del municipio
	 * @return codigo es el c&oacutedigo postal del municipio */
	public int getCodigo(){
		return codigo;}
	/**
	 * Devuelve el nombre del municipio
	 * @return nombre es el nombre del municipio */
	public String getNombre(){
		return nombre;}
	/**
	 * Devuelve la provincia a la que pertenece el municipio
	 * @return provincia es la provincia a la que pertenece el municipio */
	public IProvincia getProvincia(){
		return provincia;}
	/**
	 * Actualiza la altitud del municipio
	 * @param altitud Es la altitud del municipio */
	public void setAltitud(int altitud){
		this.altitud = altitud;}
	/**
	 * Actualiza el código postal del municipio
	 * @param codigo Es el c&oacutedigo postal del municipio */
	public void setCodigo(int codigo){
		this.codigo = codigo;}
	/**
	 * Actualiza el nombre del municipio
	 * @param nombre Es el nombre del municipio */
	public void setNombre(String nombre){
		this.nombre = nombre;}
	/**
	 * Actualiza la provincia a la que pertenece el municipio
	 * @param provincia Es la provincia a la que pertenece el municipio */
	public void setProvincia(IProvincia provincia){
		this.provincia = provincia;}
	/**
	 * Devuelve un string con el código y el nombre del municipio*/
	public String toString(){
		return nombre;}}
