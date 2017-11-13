package main;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import dao.DBRemoteAccessMunicipiosDAO;
import paa.municipios.IMunicipio;
import paa.municipios.IProvincia;

/**
 * Ventana emergente encargada de borrar un municipio de la base de datos
 * @author Javier Suarez Sanchez
 */
public class BorrarMunicipio extends JDialog{
	private static final long serialVersionUID = 1L;
	private DBRemoteAccessMunicipiosDAO bbdd = new DBRemoteAccessMunicipiosDAO();
	private JPanel centro = new JPanel(), inferior = new JPanel();
	private JLabel labelProvincia = new JLabel("Provincia:"), labelMunicipio = new JLabel("Municipio:");
	private JButton ok = new JButton("Ok"), cancelar = new JButton("Cancel");
	private JComboBox<IProvincia> inputProvincia = new JComboBox<IProvincia>();
	private JComboBox<IMunicipio> inputMunicipio = new JComboBox<IMunicipio>();
	private IProvincia provActual;
	private IMunicipio munActual;
	private boolean isOk = false;
	
	/**
	 * Constructor de la clase BorrarMunicipio
	 * @param owner Ventana padre
	 * @param title Titulo de la ventana
	 * @param modal Ventana bloqueada o no
	 * @param provinciaActual Ultima provincia seleccionada
	 * @param municipioActual Ultimo municipio seleccionado
	 */
	public BorrarMunicipio(JFrame owner, String title, boolean modal, IProvincia provinciaActual, IMunicipio municipioActual){
		super(owner,title, modal);
		this.provActual = provinciaActual;
		this.munActual = municipioActual;
		//Configuracion inicial
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setSize(500,150);
		setLocation((owner.getWidth()/2-getWidth()/2)+owner.getX(),(owner.getHeight()/2-getHeight()/2)+owner.getY());
		setLayout(new BorderLayout());
		add(centro, BorderLayout.CENTER);
		add(inferior, BorderLayout.SOUTH);
		centro.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		c.weightx = c.weighty = 1.0;
		c.gridx=0; c.gridy=0; c.gridwidth=1; c.gridheight=1;
		centro.add(labelProvincia, c);
		c.weightx = c.weighty = 3.0;
		c.gridx=1; c.gridy=0; c.gridwidth=5; c.gridheight=1;
		centro.add(inputProvincia, c);
		c.weightx = c.weighty = 1.0;
		c.gridx=0; c.gridy=1; c.gridwidth=1; c.gridheight=1;
		centro.add(labelMunicipio, c);
		c.weightx = c.weighty = 3.0;
		c.gridx=1; c.gridy=1; c.gridwidth=3; c.gridheight=1;
		centro.add(inputMunicipio, c);
		labelProvincia.setHorizontalAlignment(JLabel.RIGHT);
		labelMunicipio.setHorizontalAlignment(JLabel.RIGHT);
		inferior.setLayout(new FlowLayout());
		inferior.add(ok);
		inferior.add(cancelar);
		ok.addActionListener(new ActionOk());
		cancelar.addActionListener(new ActionCancelar());
		setProvinciasList();}
	
	
	/**
	 * Rellena la lista desplegable con las provincias
	 */
	public void setProvinciasList(){
		DefaultComboBoxModel<IProvincia> modelProvincias = new DefaultComboBoxModel<IProvincia>();
		Iterator<IProvincia> itP = ((List<IProvincia>)bbdd.getProvincias()).iterator();
		while(itP.hasNext()){
			modelProvincias.addElement(itP.next());}
		inputProvincia.setModel(modelProvincias);
		modelProvincias.setSelectedItem(provActual);
		inputProvincia.addActionListener(new ActionProvinciaSelected());
		setMunicipiosList();}
	
	
	/**
	 * Rellena la lista desplegable con los municipios
	 */
	public void setMunicipiosList(){
		if(provActual != null){
			DefaultComboBoxModel<IMunicipio> modelMunicipios = new DefaultComboBoxModel<IMunicipio>();
			Iterator<IMunicipio> itM = ((List<IMunicipio>)bbdd.getMunicipios(provActual.getCodigo())).iterator();
			while(itM.hasNext()){
				modelMunicipios.addElement(itM.next());}
			inputMunicipio.setModel(modelMunicipios);
			modelMunicipios.setSelectedItem(munActual);}}
	
	
	/**
	 * Valida los campos de la ventana
	 * @return Verdadero si los campos son validos, falso si no lo son.
	 */
	public boolean validarCampos(){
		if((inputProvincia.getSelectedItem() == null)){
			JOptionPane.showMessageDialog(BorrarMunicipio.this, "El campo 'Provincia' no puede quedar vacío");}
		else if((inputMunicipio.getSelectedItem() == null)){
			JOptionPane.showMessageDialog(BorrarMunicipio.this, "El campo 'Municipio' no puede quedar vacío");}
		else {
			return true;}
		return false;}
	
	/**
	 * Devuelve verdadero si todo esta OK, falso si no
	 * @return isOk Verdadero si todo esta ok, falso si no
	 */
	public boolean isOk(){
		return isOk;}
	
	/**
	 * Devuelve el codigo del municipio seleccionado
	 * @return Codigo de municipio seleccionado
	 */
	public int getCodMunicipio(){
		return munActual.getCodigo();}

	
	
	
	
						/* CLASES INTERNAS */
	/**
	 * Clase interna para gestionar los eventos de la lista de provincias.
	 * @author Javier Suarez Sanchez
	 */
	public class ActionProvinciaSelected implements ActionListener{
		/**
		 * Gestiona el evento recibido
		 */
		public void actionPerformed(ActionEvent e) {
			provActual = (IProvincia)inputProvincia.getSelectedItem();
			munActual = null;
			setMunicipiosList();}}
	
	/**
	 * Clase interna para gestionar el evento del boton OK
	 * @author Javier Suarez Sanchez
	 */
	public class ActionOk implements ActionListener{
		/**
		 * Borra el municipio. Si se ha llevado a cabo con éxito cierra la ventana, si no muestra un aviso.
		 */
		public void actionPerformed(ActionEvent e){
			if(validarCampos()){
				isOk = true;
				munActual = (IMunicipio)inputMunicipio.getSelectedItem();
				dispose();}}}
	
	/**
	 * Clase interna para gestionar el evento del boton OK
	 * @author Javier Suarez Sanchez
	 */
	public class ActionCancelar implements ActionListener{
		/**
		 * Cancela los cambios y cierra la ventana
		 */
		public void actionPerformed(ActionEvent e){
			dispose();}}}
