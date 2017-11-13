package main;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Ventana emergente encargada de mostrar información sobre la aplicación
 * @author Javier Suárez Sánchez
 */
public class AcercaDe extends JDialog{
	private static final long serialVersionUID = 1L;
	private JLabel labelTitulo = new JLabel("Predicción meteorológica según AEMET");
	private JLabel labelVersion = new JLabel("Versión 2.0");
	private JLabel labelAutor = new JLabel("Javier Suárez Sánchez");
	
	/**
	 * Constructor de la clase AcercaDe dada la ventana padre y si la ventana es bloqueada o no
	 * @param owner Ventana padre.
	 * @param modal Ventana bloqueante o no.
	 * */
	public AcercaDe(JFrame owner, boolean modal){
		super(owner,modal);
		setTitle("Acerca de");
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setSize(500,150);
		setLocation((owner.getWidth()/2 - getWidth()/2) + owner.getX(),(owner.getHeight()/2 - getHeight()/2) + owner.getY());
		setLayout(new GridBagLayout());
		labelTitulo.setHorizontalAlignment(JLabel.CENTER);
		labelVersion.setHorizontalAlignment(JLabel.CENTER);
		labelAutor.setHorizontalAlignment(JLabel.CENTER);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0; c.gridy = 0; c.gridwidth = 4; c.gridheight = 1;
		c.weightx = c.weighty = 1.0;
		add(labelTitulo, c);
		c.gridx = 0; c.gridy = 1; c.gridwidth = 4; c.gridheight = 1;
		c.weightx = c.weighty = 1.0;
		add(labelVersion, c);
		c.gridx = 2; c.gridy = 2; c.gridwidth = 2; c.gridheight = 1;
		c.weightx = c.weighty = 1.0;
		add(labelAutor, c);
		setVisible(true);}}
