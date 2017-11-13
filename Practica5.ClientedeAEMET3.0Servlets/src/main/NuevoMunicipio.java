package main;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import dao.DBRemoteAccessMunicipiosDAO;
import dao.Municipio;
import paa.municipios.IMunicipio;
import paa.municipios.IProvincia;

/**
 * Ventana emergente encargada de anadir un municipio a la base de datos.
 * @author Javier Suarez Sanchez
 */
public class NuevoMunicipio extends JDialog {
	private static final long serialVersionUID = 1L;
	private DBRemoteAccessMunicipiosDAO bbdd;
	private JPanel centro = new JPanel(), inferior = new JPanel();
	private JLabel nombre = new JLabel("Nombre:"), provincia = new JLabel("Provincia:"), altitud = new JLabel("Altitud:"), codigo = new JLabel("Código:");
	private JTextField inputNombre = new JTextField(), inputAltitud = new JTextField(), inputCodigo = new JTextField();
	private JComboBox<IProvincia> inputProvincia;
	private JButton botonOk = new JButton("Ok"), botonCancelar = new JButton("Cancelar");
	private IProvincia provActual = null;
	private IMunicipio munActual = null;
	private boolean isOk = false;
	
	/**
	 * Constructor de la clase Nuevo Municipio
	 * @param owner Ventana padre
	 * @param title Titulo de la ventana
	 * @param modal Modal o no
	 * @param provinciaActual Ultima provincia seleccionada
	 */
	public NuevoMunicipio(JFrame owner, String title, boolean modal, IProvincia provinciaActual){
		super(owner, title, modal);
		this.provActual = provinciaActual;
		//Configuracion inicial JFrame
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setResizable(false);
		setSize(500,200);
		setLocation((owner.getWidth() /2 - getWidth() /2) + owner.getX(),(owner.getHeight() /2 - getHeight() /2) + owner.getY());
		setLayout(new BorderLayout());
		add(centro, BorderLayout.CENTER);
		add(inferior, BorderLayout.SOUTH);
		centro.setLayout(new GridLayout(4,4,5,10));
		inferior.setLayout(new FlowLayout());
		inputProvincia = new JComboBox<IProvincia>();
		nombre.setHorizontalAlignment(JLabel.RIGHT);
		provincia.setHorizontalAlignment(JLabel.RIGHT);
		altitud.setHorizontalAlignment(JLabel.RIGHT);
		codigo.setHorizontalAlignment(JLabel.RIGHT);
		centro.add(nombre);
		centro.add(inputNombre);
		centro.add(provincia);
		centro.add(inputProvincia);
		centro.add(altitud);
		centro.add(inputAltitud);
		centro.add(codigo);
		centro.add(inputCodigo);
		inferior.add(botonOk);
		inferior.add(botonCancelar);
		//Descarga de las provincias
		setProvincias();
		//Botones
		botonOk.addActionListener(new ActionOk());
		botonCancelar.addActionListener(new ActionCancelar());}
	
	/**
	 * Rellena la lista desplegable de provincias
	 */
	public void setProvincias(){
		bbdd = new DBRemoteAccessMunicipiosDAO();
		DefaultComboBoxModel<IProvincia> modelProvincias = new DefaultComboBoxModel<IProvincia>();
		Iterator<IProvincia> itP = ((List<IProvincia>)bbdd.getProvincias()).iterator();
		while(itP.hasNext()){
			modelProvincias.addElement(itP.next());}
		modelProvincias.setSelectedItem(provActual);
		inputProvincia.setEditable(false);
		inputProvincia.setModel(modelProvincias);}

	
	/**
	 * Valida los campos de la ventana
	 * @return Verdadero si son validos, falso si alguno no lo es.
	 */
	public boolean validarCampos(){
		if(inputNombre.getText().equals("")){
			JOptionPane.showMessageDialog(NuevoMunicipio.this, "El campo 'Nombre' no puede quedar vacío.");
			return false;}
		else if(((IProvincia)inputProvincia.getSelectedItem()) == null){
			JOptionPane.showMessageDialog(NuevoMunicipio.this, "La provincia seleccionada no es válida.");
			return false;}
		else if(!intValidate(inputAltitud.getText())) {
			JOptionPane.showMessageDialog(NuevoMunicipio.this, "El campo 'Altitud' no es válido.");
			return false;}
		else if(!intValidate(inputCodigo.getText())){
			JOptionPane.showMessageDialog(NuevoMunicipio.this, "El campo 'Codigo' no es válido.");
			return false;}
		return true;}
	/**
	 * Valida si un dato es de tipo entero sin decimales
	 * @param dato Dato a validar
	 * @return Verdadero si es entero, falso si no lo es
	 */
	public boolean intValidate(String dato) {
		 try{
			 Integer.parseInt(dato);
		 }catch(NumberFormatException nfe){
			 //nfe.printStackTrace();
			 return false;}
		 return true;}
	
	/**
	 * Devuelve verdadero si todo esta OK, falso si no
	 * @return isOk Verdadero si todo esta ok, falso si no
	 */
	public boolean isOk(){
		return isOk;}
	
	
	/**
	 * Devuelve el municipio añadir
	 * @return Municipio a añadir
	 */
	public IMunicipio getMunicipio(){
		return munActual;}

	
						/* CLASES INTERNAS */
	/**
	 * Clase interna encargada de gestionar el evento del boton OK
	 * @author Javier Suarez Sanchez
	 */
	public class ActionOk implements ActionListener {
		/**
		 * Añade el municipio si los campos son validos o muestra un aviso en caso contrario
		 */
		public void actionPerformed(ActionEvent e) {
			if(validarCampos()){
				isOk = true;
				munActual = new Municipio(inputNombre.getText(), (IProvincia)inputProvincia.getSelectedItem(), Integer.parseInt(inputAltitud.getText()), Integer.parseInt(inputCodigo.getText()));
				dispose();}}}

	/**
	 * Clase interna encargada de gestionar el evento del boton cancelar
	 * @author Javier Suarez Sanchez
	 */
	public class ActionCancelar implements ActionListener{
		/**
		 * Cierra la ventana sin realizar cambios
		 */
		public void actionPerformed(ActionEvent e){
			dispose();}}}