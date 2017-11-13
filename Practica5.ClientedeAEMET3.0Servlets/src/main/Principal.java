package main;
import java.util.Scanner;

import javax.swing.JFrame;

import paa.municipios.IProvincia;

import dao.DBRemoteAccessMunicipiosDAO;
//import steps.*;
import dao.Provincia;

/**
 * Clase principal de la aplicaci�n
 * @author Javier Suarez Sanchez
 *
 */
public class Principal {
	public static void main(String args[]){
		//Inicia la interfaz de la apliacion
		/*ClientedeAEMET ae = new ClientedeAEMET("Cliente de AEMET");
		ae.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ae.pack();
		ae.setLocationRelativeTo(null);
		ae.setVisible(true);*/
		
		DBRemoteAccessMunicipiosDAO bbdd = new DBRemoteAccessMunicipiosDAO();
		String nombre, codigo;
		boolean success = false;
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("A�ADIR PROVINCIA\n");
		System.out.println("--------------------------------\n");
		System.out.println("Introduzca el nombre de la provincia:\n");
		nombre = sc.next();
		System.out.println("Introduzca el codigo de la provincia:\n");
		codigo = sc.next();
		
		System.out.println("Se va a a�adir una provincia con los siguientes par�metros:\n");
		System.out.println("-Nombre: " + nombre + "\n-Codigo: " + codigo);
		
		success = bbdd.addProvincia((IProvincia)new Provincia(nombre, Integer.parseInt(codigo)));
		
		System.out.println("\n�La operaci�n se realiz� con �xito? " + success);
	
	
	}}
