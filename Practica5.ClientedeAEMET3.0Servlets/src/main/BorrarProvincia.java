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
import paa.municipios.IProvincia;

/**
 * Ventana emergente encargada de borrar provincia en la base de datos.
 * @author Javier Suarez Sanchez
 */
public class BorrarProvincia extends JDialog{
	private static final long serialVersionUID = 1L;
	private DBRemoteAccessMunicipiosDAO bbdd;
	private JPanel centro = new JPanel(), inferior = new JPanel();
	private JLabel labelProvincia = new JLabel("Provincia:");
	private JButton ok = new JButton("Ok"), cancelar = new JButton("Cancel");
	private JComboBox<IProvincia> inputProvincia = new JComboBox<IProvincia>();
	private IProvincia provActual;
	private boolean isOk = false;
	
	/**
	 * Constructor de la clase BorrarProvincia
	 * @param owner Ventana padre
	 * @param title Titulo de la ventana
	 * @param modal Ventana bloqueada o no
	 * @param provinciaActual Ultima provincia seleccionada.
	 */
	public BorrarProvincia(JFrame owner, String title, boolean modal, IProvincia provinciaActual){
		super(owner,title, modal);
		this.provActual = provinciaActual;
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setSize(500,120);
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
		labelProvincia.setHorizontalAlignment(JLabel.RIGHT);
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
		bbdd = new DBRemoteAccessMunicipiosDAO();
		DefaultComboBoxModel<IProvincia> modelProvincias = new DefaultComboBoxModel<IProvincia>();
		Iterator<IProvincia> itP = ((List<IProvincia>)bbdd.getProvincias()).iterator();
		while(itP.hasNext()){
			modelProvincias.addElement(itP.next());}
		modelProvincias.setSelectedItem(provActual);
		inputProvincia.setEditable(false);
		inputProvincia.setModel(modelProvincias);	}
	
	
	/**
	 * Valida los campos de la ventana
	 * @return Verdadero si los campos son válidos, falso si no lo son
	 */
	public boolean validarCampos(){
		if((inputProvincia.getSelectedItem() == null)){
			JOptionPane.showMessageDialog(BorrarProvincia.this, "El campo 'Provincia' no puede quedar vacío");}
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
	 * Devuelve el codigo de la provincia seleccionada
	 * @return Codigo de provincia seleccionada
	 */
	public int getCodProvincia(){
		return provActual.getCodigo();}

	
	
	
	
	
	
	
						/* CLASES INTERNAS */
	/**
	 * Clase interna encargada de gestionar el evento del boton OK
	 * @author Javier Suarez Sanchez
	 */
	public class ActionOk implements ActionListener{
		/**
		 * Valida los campos y si son correctos borra la provincia seleccionada, si no muestra un aviso. Si algún campo no es correcto también muestra un aviso.
		 */
		public void actionPerformed(ActionEvent e){
			if(validarCampos()){
				provActual = (IProvincia)inputProvincia.getSelectedItem();
				isOk = true;
				dispose();}}}
	/**
	 * Clase interna encargada de gestionar el evento del boton cancelar
	 * @author Javier Suarez Sanchez
	 */
	public class ActionCancelar implements ActionListener{
		/**
		 * Cancela los cambios y cierra ventana
		 */
		public void actionPerformed(ActionEvent e){
			dispose();}}}
