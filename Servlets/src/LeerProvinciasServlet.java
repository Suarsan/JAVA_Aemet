import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import paa.municipios.IProvincia;
import dao.DBAccessMunicipiosDAO;

/**
 * Clase encargada de gestionar la peticion de lectura las provincias, realiza la petición a la bbdd y devuelve los resultados
 * @author Javier Suarez Sanchez
 */
public class LeerProvinciasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBAccessMunicipiosDAO bbdd;
	private Collection<IProvincia> provinciasList;

	public void init(ServletConfig servletConfig) throws ServletException {
	    super.init(servletConfig);}
	
	@SuppressWarnings("unchecked")
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		bbdd = new DBAccessMunicipiosDAO();
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		//Declaraciones
		JSONObject main = new JSONObject();
		JSONArray provinciasArray = new JSONArray();
		JSONObject provincia;
		IProvincia auxProv;
		Iterator<IProvincia> it;

		//Devuelve las provincias
		provinciasList = (ArrayList<IProvincia>)bbdd.getProvincias();
		it = provinciasList.iterator();		
		
		//Añado una a una las provincias al array JSON
		while(it.hasNext()){
			auxProv = it.next();
			provincia = new JSONObject();
			provincia.put("codigo", auxProv.getCodigo());
			provincia.put("nombre", auxProv.getNombre());
			provinciasArray.add(provincia);}
		
		//Añado el array al campo del objeto con id "provincias"
		main.put("provincias", provinciasArray);
		
		out.println (main.toJSONString());
		out.close();
		System.out.println("leyendoProvincias...");} 
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {}
    @Override
    public String getServletInfo() {
        return "Este servlet proporciona info"
                + " sobre de una almacen de poblaciones";
    }
}
