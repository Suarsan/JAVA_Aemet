package main;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import paa.municipios.IPrediccion;
/**
 * Modelo para JTree
 * @author Javier Suarez Sanchez
 *
 */
public class MisPrediccionesModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Fecha", "Temp. Min", "Temp. Max", "Icono", "Estado del cielo"};
	List<IPrediccion> predicciones;
	public MisPrediccionesModel(){}
	
	public void setPredicciones(List<IPrediccion> predicciones){
		this.predicciones = predicciones;}
	
	public int getRowCount() {
		if(predicciones != null){
			return predicciones.size();}
		return 0;}

	public int getColumnCount() {
		return columnNames.length;}
	
	public String getColumnName(int col){
		return columnNames[col];}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj = null;
		switch(columnIndex){
			case 0:
				obj = predicciones.get(rowIndex).getFecha();
			break;
			case 1:
				obj = predicciones.get(rowIndex).getTemperaturaMinima();
			break;
			case 2:
				obj = predicciones.get(rowIndex).getTemperaturaMaxima();
			break;
			case 3:
				obj = predicciones.get(rowIndex).getIconoEstadoCielo();
			break;
			case 4:
				obj = predicciones.get(rowIndex).getEstadoCielo();
			break;}
		return obj;}
	
	public Class<?> getColumnClass(int c){
		return getValueAt(0,c).getClass();}

}
