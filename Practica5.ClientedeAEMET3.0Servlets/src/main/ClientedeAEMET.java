package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import dao.DBRemoteAccessMunicipiosDAO;
import paa.municipios.GestorAEMET;
import paa.municipios.GestorAEMETException;
import paa.municipios.IMunicipio;
import paa.municipios.IPrediccion;
import paa.municipios.IProvincia;

/**
 * Interfaz principal de la aplicación
 * @author Javier Suarez Sanchez
 */
public class ClientedeAEMET extends JFrame{
	private static final long serialVersionUID = 1L;
	private DBRemoteAccessMunicipiosDAO bbdd;
	private Map<Integer, List<IPrediccion>> localBBDD = new HashMap<Integer, List<IPrediccion>>();
	private JMenuBar menubar = new JMenuBar();
	private JMenu archivo = new JMenu("Archivo"), municipios = new JMenu("Municipios"), provincias = new JMenu("Provincias"), ayuda = new JMenu("Ayuda");
	private JMenuItem actualizar = new JMenuItem("Actualizar predicciones"), salir = new JMenuItem("Salir");
	private JMenuItem nuevoMunicipio = new JMenuItem("Nuevo municipio"), borrarMunicipio = new JMenuItem("Borrar municipio"), nuevaProvincia = new JMenuItem("Nueva provincia"), borrarProvincia = new JMenuItem("Borrar provincia");
	private JMenuItem acercade = new JMenuItem("Acerca de");
	private JPanel centro = new JPanel(), inferior = new JPanel(), centroDcha = new JPanel();
	private JToolBar superior = new JToolBar();
	private JButton botonActualizar = new JButton("Actualizar"), botonAnadir = new JButton("+ Municipio"), botonBorrar = new JButton("- Municipio");
	private JScrollPane scrollTree;
	private ImageIcon actualizarImage, anadirImage, borrarImage;
	private Collection<IProvincia> provinciasColl;
	private Collection<IMunicipio> municipiosColl;
	private IProvincia provActual = null;
	private IMunicipio munActual = null;
	private JLabel barraEstado, tituloTabla;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode top;
	private String estado = "Cliente AEMET 5.0 - Tomcat mediante Servlets";
	private JTable table;
	private MisPrediccionesModel tableModel;
	private JScrollPane scrollTable;
	private JSplitPane splitPane;

	
	/**
	 * Constructor de la clase ClientedeAEMET dado el titulo
	 * @param titulo Título de la ventana
	 */
	public ClientedeAEMET(String titulo){
		super(titulo);
		bbdd = new DBRemoteAccessMunicipiosDAO();
		actualizar.addActionListener(new ActionActualizarPredicciones());
		nuevoMunicipio.addActionListener(new ActionNuevoMunicipio());
		borrarMunicipio.addActionListener(new ActionBorrarMunicipio());
		nuevaProvincia.addActionListener(new ActionNuevaProvincia());
		borrarProvincia.addActionListener(new ActionBorrarProvincia());
		acercade.addActionListener(new ActionAcercaDe());
		salir.addActionListener(new ActionSalir());
		menubar.add(archivo);
		menubar.add(municipios);
		menubar.add(provincias);
		menubar.add(ayuda);
		archivo.add(actualizar);
		archivo.add(salir);
		municipios.add(nuevoMunicipio);
		municipios.add(borrarMunicipio);
		provincias.add(nuevaProvincia);
		provincias.add(borrarProvincia);
		ayuda.add(acercade);
		setShortcuts();
		setJMenuBar(menubar);
		/*    MainLayout    */
		setLayout(new BorderLayout());
		superior.setLayout(new FlowLayout(FlowLayout.LEFT));
		centro.setLayout(new GridLayout(1,1,5,10));
		inferior.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(superior, BorderLayout.NORTH);
		add(centro, BorderLayout.CENTER);
		add(inferior, BorderLayout.SOUTH);
		/*    North    */
		actualizarImage = new ImageIcon("includes/images/actualizar.png");
		anadirImage = new ImageIcon("includes/images/anadir.png");
		borrarImage = new ImageIcon("includes/images/eliminar.png");
		botonActualizar = new JButton("Actualizar", actualizarImage);
		botonAnadir = new JButton("Municipio", anadirImage);
		botonBorrar = new JButton("Municipio", borrarImage);
		botonActualizar.addActionListener(new ActionActualizarPredicciones());
		botonAnadir.addActionListener(new ActionNuevoMunicipio());
		botonBorrar.addActionListener(new ActionBorrarMunicipio());
		superior.add(botonActualizar);
		superior.add(botonAnadir);
		superior.add(botonBorrar);
		superior.setBorder(new EmptyBorder(20,5,10,5));
		/*    SOUTH    */
		barraEstado = new JLabel(estado);
		inferior.setBackground(Color.LIGHT_GRAY);
		inferior.setBorder(new EmptyBorder(0,5,0,0));
		inferior.add(barraEstado);
		/*     CENTER    */
		/*        Â¬IZQ SPLIT   */
		top = new DefaultMutableTreeNode("Poblaciones");
		treeModel = new DefaultTreeModel(top);
		tree = new JTree(treeModel);
		scrollTree = new JScrollPane(tree);
		tree.setModel(treeModel);
		setTree();
		/*        Â¬DCHA SPLIT   */
		tableModel = new MisPrediccionesModel();
		table = new JTable(tableModel);
		scrollTable = new JScrollPane(table);
		centroDcha.setLayout(new BorderLayout());
		tituloTabla = new JLabel("Predicciones");
		centroDcha.add(tituloTabla, BorderLayout.NORTH);
		centroDcha.add(scrollTable, BorderLayout.CENTER);
		//scrollTable = new JScrollPane(centroDcha);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollTree, centroDcha);
		centro.add(splitPane);
		setPredicciones();}
	
	/**
	 * Rellena el arbol de provincias
	 */
	public void setTree(){
		provinciasColl = bbdd.getProvincias();
		top.removeAllChildren();
		//treeModel.reload();
		int i=0;
		for(IProvincia p : provinciasColl){
			DefaultMutableTreeNode auxProvNode = new DefaultMutableTreeNode(p);
				treeModel.insertNodeInto(auxProvNode, top, i);	
			int j = 0;
			municipiosColl = bbdd.getMunicipios(p.getCodigo());
			for(IMunicipio m : municipiosColl){
				DefaultMutableTreeNode auxMunNode = new DefaultMutableTreeNode(m);
					treeModel.insertNodeInto(auxMunNode, auxProvNode, j);
					j++;}
			i++;}
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new ActionItemSelectedJTree());
		treeModel.reload();}
	
	
	 /**
	  * Define la información a mostrar en la zona de predicciones segun el caso
	  */
	public void setPredicciones(){
		List<IPrediccion> predicciones = null;
		if(munActual != null){
			tituloTabla.setText("Predicciones para " + munActual.toString());
			//Local
			if(localBBDD.containsKey(munActual.getCodigo())){ 
				predicciones = localBBDD.get(munActual.getCodigo());
				tableModel.setPredicciones(predicciones);
				tableModel.fireTableDataChanged();
			//Online
			}else{
				actualizarPredicciones();}}}
	
	/**
	 * Fuerza la actualización de las predicciones del servicio online de AEMET
	 */
	public void actualizarPredicciones(){
		List<IPrediccion> predicciones = null;
		GestorAEMET gestor = new GestorAEMET();
		try{
			if(munActual != null){
				predicciones = gestor.getPredicciones(munActual);
				tableModel.setPredicciones(predicciones);
				tableModel.fireTableDataChanged();
				localBBDD.put(munActual.getCodigo(), predicciones);}
		}catch(GestorAEMETException gae){
			gae.printStackTrace();}}
	
	
	/**
	 * Establece los atajos del teclado para la barra de menu
	 */
	public void setShortcuts(){
		archivo.setMnemonic(KeyEvent.VK_A);
		actualizar.setMnemonic(KeyEvent.VK_T);
		salir.setMnemonic(KeyEvent.VK_S);
		municipios.setMnemonic(KeyEvent.VK_M);
		nuevoMunicipio.setMnemonic(KeyEvent.VK_N);
		borrarMunicipio.setMnemonic(KeyEvent.VK_B);
		provincias.setMnemonic(KeyEvent.VK_P);
		nuevaProvincia.setMnemonic(KeyEvent.VK_N);
		borrarProvincia.setMnemonic(KeyEvent.VK_B);
		ayuda.setMnemonic(KeyEvent.VK_U);
		acercade.setMnemonic(KeyEvent.VK_C);}
	
		
						/* CLASES INTERNAS */
	/**
	 * Clase interna encargada de gestionar los eventos del arbol
	 * @author Javier Suarez Sanchez
	 */
	public class ActionItemSelectedJTree implements TreeSelectionListener{
		/**
		 * Acutaliza el municipio y provincia pulsado
		 */
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			if(selectedNode != null){
				if(selectedNode.getUserObject() instanceof IProvincia){
					provActual = (IProvincia)selectedNode.getUserObject();
				}else if(selectedNode.getUserObject() instanceof IMunicipio ){
					munActual = (IMunicipio)selectedNode.getUserObject();
					provActual = (IProvincia)munActual.getProvincia();}}
			setPredicciones();}}
	
	/**
	 * Clase interna encargada de gestionar el evento para actualizar predicciones desde AEMET online
	 * @author Javier Suarez Sanchez
	 */
	public class ActionActualizarPredicciones implements ActionListener{
		/**
		 * Actualiza las predicciones desde AEMET online
		 */
		public void actionPerformed(ActionEvent e){ 
			actualizarPredicciones();}}
	
	
	/**
	 * Clase interna encargada de gestionar el evento para abrir la ventana Nuevo Municipio
	 * @author Javier Suarez Sanchez
	 */
	public class ActionNuevoMunicipio extends JDialog implements ActionListener{
		private static final long serialVersionUID = 1L;
		/**
		 * Abre la ventana Nuevo Municipio
		 */
		public void actionPerformed(ActionEvent e){
			String msg;
			NuevoMunicipio nm = new NuevoMunicipio(ClientedeAEMET.this, "Nuevo Municipio", true, provActual);
			nm.setVisible(true);
			if(nm.isOk()){
				if(bbdd.addMunicipio(nm.getMunicipio())){
					msg = "Municipio añadido con éxito.";
					setTree();
				}else{
					msg = "El municipio no se pudo añadir.";}
				JOptionPane.showMessageDialog(ClientedeAEMET.this, msg);}}}
	
	
	/**
	 * Clase interna encargada de gestionar el evento para abrir la ventana Borrar Municipio
	 * @author Javier Suarez Sanchez
	 */
	public class ActionBorrarMunicipio extends JDialog implements ActionListener{
		private static final long serialVersionUID = 1L;
		/**
		 * Abre la ventana Borrar Municipio
		 */
		public void actionPerformed(ActionEvent e){
			String msg;
			BorrarMunicipio bm = new BorrarMunicipio(ClientedeAEMET.this,"Borrar Municipio", true, provActual, munActual);
			bm.setVisible(true);
			if(bm.isOk()){
				if(bbdd.delMunicipio(bm.getCodMunicipio())){
					msg = "Municipio borrado con éxito.";
					setTree();
				}else{
					msg = "El municipio no se pudo borrar.";}
			JOptionPane.showMessageDialog(ClientedeAEMET.this, msg);}}}
	
	
	/**
	 * Clase interna encargada de gestionar el evento para abrir la ventana Nueva Provincia
	 * @author Javier Suarez Sanchez
	 */
	public class ActionNuevaProvincia extends JDialog implements ActionListener{
		private static final long serialVersionUID = 1L;
		/**
		 * Abre la ventana Nueva Provincia
		 */
		public void actionPerformed(ActionEvent e){
			String msg;
			NuevaProvincia np = new NuevaProvincia(ClientedeAEMET.this, "Nueva Provincia", true);
			np.setVisible(true);
			if(np.isOk()){
				if(bbdd.addProvincia(np.getProvincia())){
					msg = "Provincia añadida con éxito";
					setTree();
				}else{
					msg = "La provincia no se pudo añadir. Quizás ya esté añadida.";}
				JOptionPane.showMessageDialog(ClientedeAEMET.this, msg);}}}
	
	
	/**
	 * Clase interna encargada de gestionar el evento para abrir la ventana Nueva Provincia
	 * @author Javier Suarez Sanchez
	 */
	public class ActionBorrarProvincia extends JDialog implements ActionListener{
		private static final long serialVersionUID = 1L;
		/**
		 * Abre la ventana Nueva Provincia
		 */
		public void actionPerformed(ActionEvent e){
			String msg;
			BorrarProvincia bp = new BorrarProvincia(ClientedeAEMET.this,"Borrar Provincia", true, provActual);
			bp.setVisible(true);
			if(bp.isOk()){
				if(bbdd.delProvincia(bp.getCodProvincia())){
					msg = "Provincia borrada con éxito";
					setTree();
				}else{
					msg = "La provincia no se pudo borrar.";}
				JOptionPane.showMessageDialog(ClientedeAEMET.this, msg);}}}
	
	
	/**
	 * Clase interna encargada de gestionar el evento para abrir la ventana AcercaDe
	 * @author Javier Suarez Sanchez
	 */
	public class ActionAcercaDe extends JDialog implements ActionListener{
		private static final long serialVersionUID = 1L;
		/**
		 * Abre la ventana AcercaDe
		 */
		public void actionPerformed(ActionEvent e){
			new AcercaDe(ClientedeAEMET.this,true);}}
	
	
	/**
	 * Clase interna encargada de gestionar el evento salir de la aplicacion
	 * @author Javier Suarez Sanchez
	 */
	public class ActionSalir implements ActionListener{
		/**
		 * Salir de la aplicacion
		 */
		public void actionPerformed(ActionEvent e) {
			int jop = JOptionPane.showConfirmDialog(ClientedeAEMET.this, "¿Está seguro de que desea salir? Se borrarán las predicciones almacenadas en la base de datos local.", "Aviso", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			 if(jop == JOptionPane.OK_OPTION){
		           System.exit(0);}}}
	}
