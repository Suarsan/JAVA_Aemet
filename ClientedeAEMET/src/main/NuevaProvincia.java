package main;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import dao.Provincia;
import paa.municipios.IProvincia;

/**
 * Ventana emergente encargada de añadir una provincia a la base de datos.
 * @author Javier Suarez Sanchez
 */
public class NuevaProvincia extends JDialog {
	private static final long serialVersionUID = 1L;
	private JPanel centro = new JPanel(), inferior = new JPanel();
	private JLabel nombre = new JLabel("Nombre:"), codigo = new JLabel("Código:");
	private JTextField inputNombre = new JTextField(), inputCodigo = new JTextField();
	private JButton botonOk = new JButton("Ok"), botonCancelar = new JButton("Cancelar");
	private IProvincia provActual;
	private boolean isOk = false;
	
	/**
	 * Constructor de la clase NuevaProvincia
	 * @param owner Ventana padre
	 * @param title Titulo de la ventana
	 * @param modal Si es modal o no
	 */
	public NuevaProvincia(JFrame owner, String title, boolean modal){
		super(owner, title, modal);
		//Configuracion inicial JFrame
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setResizable(false);
		setSize(500,150);
		setLocation((owner.getWidth() /2 - getWidth() /2) + owner.getX(),(owner.getHeight() /2 - getHeight() /2) + owner.getY());
		setLayout(new BorderLayout());
		add(centro, BorderLayout.CENTER);
		add(inferior, BorderLayout.SOUTH);
		centro.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		c.weightx = c.weighty = 1.0;
		c.gridx=0; c.gridy=0; c.gridwidth=1; c.gridheight=1;
		centro.add(nombre,c);
		
		c.weightx = c.weighty = 3.0;
		c.gridx=1; c.gridy=0; c.gridwidth=5; c.gridheight=1;
		centro.add(inputNombre, c);
		
		c.weightx = c.weighty = 1.0;
		c.gridx=0; c.gridy=1; c.gridwidth=1; c.gridheight=1;
		centro.add(codigo, c);
		
		c.weightx = c.weighty = 3.0;
		c.gridx=1; c.gridy=1; c.gridwidth=3; c.gridheight=1;
		centro.add(inputCodigo, c);
		
		nombre.setHorizontalAlignment(JLabel.RIGHT);
		codigo.setHorizontalAlignment(JLabel.RIGHT);
		inferior.setLayout(new FlowLayout());
		inferior.add(botonOk);
		inferior.add(botonCancelar);
		//Botones
		botonOk.addActionListener(new ActionOk());
		botonCancelar.addActionListener(new ActionCancelar());}
	
	
	
	
	/**
	 * Valida los campos de la ventana
	 * @return Verdadero si son validos, falso si no
	 */
	public boolean validarCampos(){
		if(inputNombre.getText().equals("")){
			JOptionPane.showMessageDialog(NuevaProvincia.this, "El campo 'Nombre' no puede quedar vacío.");
			return false;}
		else if(!intValidate(inputCodigo.getText())){
			JOptionPane.showMessageDialog(NuevaProvincia.this, "El campo 'Codigo' no es válido.");
			return false;}
		return true;}
	
	
	/**
	 * Valida si un dato es de tipo entero sin decimales
	 * @param dato
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
	 * Devuelve la provincia a anadir
	 * @return Provincia a anadir
	 */
	public IProvincia getProvincia(){
		return provActual;}

	
	
					/* CLASES INTERNAS */
	/**
	 * Clase interna encargada de gestionar el evento del botón OK
	 * @author Javier Suarez Sanchez
	 */
	public class ActionOk implements ActionListener {
		/**
		 * A&nacuteade la provincia si los campos son validos o muestra un aviso en caso contrario
		 */
		public void actionPerformed(ActionEvent e) {
			if(validarCampos()){
				provActual = new Provincia(inputNombre.getText(), Integer.parseInt(inputCodigo.getText()));
				isOk = true;
				dispose();}}}
	/**
	 * Clase interna encargada de gestionar el evento del botón cancelar
	 * @author Javier Suarez Sanchez
	 */
	public class ActionCancelar implements ActionListener{
		/**
		 * Cierra la ventana sin realizar cambios
		 */
		public void actionPerformed(ActionEvent e){
			dispose();}}}